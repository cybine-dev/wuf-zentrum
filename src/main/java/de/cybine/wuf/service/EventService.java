package de.cybine.wuf.service;

import de.cybine.quarkus.util.api.*;
import de.cybine.quarkus.util.api.query.*;
import de.cybine.quarkus.util.datasource.*;
import de.cybine.wuf.data.event.*;
import io.quarkus.runtime.*;
import jakarta.inject.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Startup
@Singleton
@RequiredArgsConstructor
public class EventService
{
    // @formatter:off
    private final GenericApiQueryService<EventEntity, Event> service =
            GenericApiQueryService.forType(EventEntity.class, Event.class);
    // @formatter:on

    public List<Event> fetchEventsEndingAfter(ZonedDateTime endsAt)
    {
        DatasourceConditionDetail<ZonedDateTime> endsAfter = DatasourceHelper.isGreater(EventEntity_.ENDS_AT, endsAt);
        DatasourceQuery query = DatasourceQuery.builder().condition(DatasourceHelper.and(endsAfter)).build();

        return this.fetch(query);
    }

    public List<Event> fetch(DatasourceQuery query)
    {
        return this.service.fetch(query);
    }

    public List<Event> fetch(ApiQuery query)
    {
        return this.service.fetch(query);
    }

    public Optional<Event> fetchSingle(ApiQuery query)
    {
        return this.service.fetchSingle(query);
    }

    public List<Map<String, Object>> fetchOptions(ApiQuery query)
    {
        return this.service.fetchOptions(query);
    }

    public List<ApiCountInfo> fetchTotal(ApiQuery query)
    {
        return this.service.fetchTotal(query);
    }
}
