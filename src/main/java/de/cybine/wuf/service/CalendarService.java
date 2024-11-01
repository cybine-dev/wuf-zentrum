package de.cybine.wuf.service;

import biweekly.*;
import biweekly.component.*;
import biweekly.io.*;
import biweekly.property.*;
import biweekly.util.Duration;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import de.cybine.quarkus.util.action.data.Action;
import de.cybine.quarkus.util.action.data.*;
import de.cybine.quarkus.util.converter.*;
import de.cybine.quarkus.util.datasource.*;
import de.cybine.wuf.api.ext.*;
import de.cybine.wuf.config.ApplicationConfig;
import de.cybine.wuf.config.*;
import de.cybine.wuf.data.event.*;
import de.cybine.wuf.data.organizer.*;
import de.cybine.wuf.data.raw.*;
import de.cybine.wuf.service.action.*;
import io.quarkus.runtime.*;
import io.quarkus.scheduler.*;
import jakarta.annotation.*;
import jakarta.inject.*;
import jakarta.persistence.*;
import jakarta.transaction.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.eclipse.microprofile.rest.client.*;

import java.net.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@Slf4j
@Startup
@Singleton
@RequiredArgsConstructor
public class CalendarService
{
    private static final TypeReference<List<WufEvent>> WUF_EVENT_TYPE = new TypeReference<>() { };

    private final ObjectMapper  objectMapper;
    private final EntityManager entityManager;

    private final EventService      eventService;
    private final ActionService     actionService;
    private final EventDataService  eventDataService;
    private final ConverterRegistry converterRegistry;
    private final ApplicationConfig applicationConfig;

    // Make sure beans have been created
    @SuppressWarnings("unused")
    private final ActionConfig actionConfig;

    @SuppressWarnings("unused")
    private final ConverterRegistryConfig converterRegistryConfig;

    private WufApi apiClient;

    @PostConstruct
    void setup( )
    {
        this.apiClient = RestClientBuilder.newBuilder()
                                          .baseUri(this.applicationConfig.api().address())
                                          .build(WufApi.class);

        if (this.eventDataService.fetchCurrentData("events", null).isEmpty())
            this.fetchEvents();
    }

    @SneakyThrows
    @Transactional
    @Scheduled(cron = "${quarkus.app.ext-api.fetch-interval}")
    void fetchEvents( )
    {
        log.info("Start fetching calendar...");
        LocalDate today = LocalDate.now();
        List<WufEvent> events = new ArrayList<>();
        events.addAll(this.apiClient.fetchEvents(today.minusMonths(1L).toString()));
        events.addAll(this.apiClient.fetchEvents(today.toString()));
        events = events.stream().distinct().toList();

        String value = this.objectMapper.writeValueAsString(events);
        EventDataEntity eventData = EventDataEntity.builder()
                                                   .id(EventDataId.create().getValue())
                                                   .createdAt(ZonedDateTime.now())
                                                   .type("events")
                                                   .externalId("events")
                                                   .data(value)
                                                   .build();

        log.info("Persisting calendar data...");
        this.entityManager.persist(eventData);
        this.processEventUpdates();
        log.info("Finished processing fetched calendar data");
    }

    @SneakyThrows
    @Transactional
    public void processEventUpdates( )
    {
        ZonedDateTime beginOfDay = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS);

        String data = this.eventDataService.fetchCurrentData("events", null).orElseThrow().getData();
        Map<String, Event> persistent = this.eventService.fetchEventsEndingAfter(beginOfDay)
                                                         .stream()
                                                         .collect(Collectors.toMap(Event::getExternalId,
                                                                 Function.identity()));

        Map<String, Event> update = this.converterRegistry.getProcessor(WufEvent.class, Event.class)
                                                          .toList(this.objectMapper.readValue(data, WUF_EVENT_TYPE))
                                                          .result()
                                                          .stream()
                                                          .collect(Collectors.toMap(Event::getExternalId,
                                                                  Function.identity()));

        log.info("Starting calendar sync...");
        log.info("Processing {} persisted and {} fetched events", persistent.size(), update.size());

        String correlationId = this.actionService.beginWorkflow(ActionConfig.WUF_NAMESPACE,
                ActionConfig.CALENDAR_CATEGORY, ActionConfig.CALENDAR_SYNC_NAME);

        log.info("Registering unknown events...");
        this.actionService.bulkPerform(update.values()
                                             .stream()
                                             .filter(item -> !persistent.containsKey(item.getExternalId()))
                                             .map(ActionData::of)
                                             .map(item -> Action.of(this.createSyncMetadata(correlationId,
                                                     ActionConfig.REGISTER_EVENT_ACTION), item))
                                             .toList());

        log.info("Updating known events...");
        this.actionService.bulkPerform(update.values()
                                             .stream()
                                             .filter(item -> item.getEndsAt().isAfter(beginOfDay))
                                             .filter(item -> persistent.containsKey(item.getExternalId()))
                                             .map(item -> EventDiff.of(persistent.get(item.getExternalId()), item))
                                             .filter(EventDiff::hasDiff)
                                             .map(ActionData::of)
                                             .map(item -> Action.of(this.createSyncMetadata(correlationId,
                                                     ActionConfig.UPDATE_EVENT_ACTION), item))
                                             .toList());

        log.info("Removing canceled events...");
        this.actionService.bulkPerform(persistent.values()
                                                 .stream()
                                                 .filter(item -> !update.containsKey(item.getExternalId()))
                                                 .map(ActionData::of)
                                                 .map(item -> Action.of(this.createSyncMetadata(correlationId,
                                                         ActionConfig.REMOVE_EVENT_ACTION), item))
                                                 .toList());

        this.actionService.perform(
                Action.of(this.createSyncMetadata(correlationId, ActionService.TERMINATED_STATE), null));
        log.info("Finished calendar sync");
    }

    public String getCalendar( )
    {
        ICalendar calendar = new ICalendar();
        calendar.getTimezoneInfo().setDefaultTimezone(TimezoneAssignment.download(TimeZone.getDefault(), true));
        calendar.setRefreshInterval(Duration.builder().hours(1).build());
        calendar.setVersion(ICalVersion.V2_0);
        calendar.setMethod(Method.publish());
        calendar.setName("WuF-Zentrum");
        calendar.setDescription("Terminkalender WuF-Zentrum");
        calendar.setLastModified(this.transformLocalDateTime(ZonedDateTime.now()));

        String serviceName = this.applicationConfig.serviceName();
        String version = this.getClass().getPackage().getImplementationVersion();
        calendar.setProductId(String.format("-//Cybine//%s v%s//DE", serviceName, version));

        DatasourceQuery query = DatasourceQuery.builder()
                                               .relation(DatasourceHelper.fetch(EventEntity_.ORGANIZER))
                                               .build();

        List<Event> events = this.eventService.fetch(query);
        events.stream().map(this::getEvent).forEach(calendar::addEvent);

        return calendar.write();
    }

    private VEvent getEvent(Event data)
    {
        VEvent event = new VEvent();
        event.setUid(data.getId().getValue().toString() + "@wuf-zentrum-relay.cybine.dev");
        event.setClassification(Classification.public_());
        event.setSummary(data.getTitle());

        event.setOrganizer(new Organizer("Unbekannt", this.applicationConfig.email()));
        EventOrganizer organizer = data.getOrganizer().orElse(null);
        if (organizer != null)
        {
            String name = "";
            String firstname = organizer.getFirstname().orElse(null);
            if (firstname != null)
                name = firstname;

            String lastname = organizer.getLastname().orElse(null);
            if (lastname != null)
                name += (name.isEmpty() ? "" : " ") + lastname;

            if (name.isEmpty())
                name = null;

            event.setOrganizer(new Organizer(name, organizer.getEmail().orElse(null)));
        }

        event.setDescription(this.getDescription(data));
        event.setLocation(data.getAddress().orElse(null));
        event.setDateStart(this.transformLocalDateTime(data.getStartsAt()));
        event.setDateEnd(this.transformLocalDateTime(data.getEndsAt()));

        return event;
    }

    private Date transformLocalDateTime(ZonedDateTime dateTime)
    {
        return Date.from(dateTime.toInstant());
    }

    private String getDescription(Event event)
    {
        List<String> lines = new ArrayList<>();
        event.getLink().map(URI::toString).map(item -> String.format("Weitere Infos: %s", item)).ifPresent(lines::add);

        return String.join("\n", lines);
    }

    private ActionMetadata createSyncMetadata(String correlationId, String action)
    {
        return ActionMetadata.builder()
                             .namespace(ActionConfig.WUF_NAMESPACE)
                             .category(ActionConfig.CALENDAR_CATEGORY)
                             .name(ActionConfig.CALENDAR_SYNC_NAME)
                             .correlationId(correlationId)
                             .source(this.applicationConfig.serviceName())
                             .action(action)
                             .build();
    }
}
