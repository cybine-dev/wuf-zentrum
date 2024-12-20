package de.cybine.wuf.service.calendar;

import de.cybine.quarkus.util.action.*;
import de.cybine.quarkus.util.action.data.*;
import de.cybine.quarkus.util.converter.*;
import de.cybine.wuf.data.event.*;
import de.cybine.wuf.data.organizer.*;
import io.quarkus.arc.*;
import jakarta.persistence.*;
import lombok.experimental.*;
import lombok.extern.slf4j.*;

import java.net.*;

@Slf4j
@UtilityClass
public class EventActionProcessor
{
    public static ActionResult<Event> register(Action action, ActionHelper helper)
    {
        EntityManager entityManager = Arc.container().select(EntityManager.class).get();
        ConverterRegistry converterRegistry = Arc.container().select(ConverterRegistry.class).get();

        Event event = action.<Event>getData().orElseThrow().value();
        EventEntity entity = converterRegistry.getProcessor(Event.class, EventEntity.class).toItem(event).result();
        entity.setId(EventId.create().getValue());
        entityManager.persist(entity);

        return helper.createResult(
                converterRegistry.getProcessor(EventEntity.class, Event.class).toItem(entity).result());
    }

    public static ActionResult<Event> remove(Action action, ActionHelper helper)
    {
        EntityManager entityManager = Arc.container().select(EntityManager.class).get();

        Event event = action.<Event>getData().orElseThrow().value();
        entityManager.createNativeQuery(
                             String.format("UPDATE %s SET %s = :status WHERE %s = :id", EventEntity_.TABLE,
                                     EventEntity_.STATUS_COLUMN,
                                     EventEntity_.ID_COLUMN), Void.class)
                     .setParameter("status", EventStatus.ARCHIVED.name())
                     .setParameter("id", event.getId())
                     .executeUpdate();

        return helper.createResult(event);
    }

    public static ActionResult<EventDiff> update(Action action, ActionHelper helper)
    {
        EntityManager entityManager = Arc.container().select(EntityManager.class).get();

        EventDiff diff = action.<EventDiff>getData().orElseThrow().value();
        Event next = diff.getNext();

        EventEntity event = entityManager.find(EventEntity.class, diff.getPrevious().getId().getValue());
        event.setTitle(next.getTitle());
        event.setStartsAt(next.getStartsAt());
        event.setEndsAt(next.getEndsAt());
        event.setLink(next.getLink().map(URI::toString).orElse(null));
        event.setAddress(next.getAddress().orElse(null));
        event.setOrganizer(next.getOrganizerId()
                               .map(item -> EventOrganizerEntity.builder().id(item.getValue()).build())
                               .orElse(null));
        event.setStatus(next.getStatus());

        return helper.createResult(diff);
    }

    public static boolean updateWhen(Action action, ActionHelper helper)
    {
        return action.<EventDiff>getData().orElseThrow().value().hasDiff();
    }
}
