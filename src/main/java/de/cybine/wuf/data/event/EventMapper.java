package de.cybine.wuf.data.event;

import de.cybine.quarkus.data.util.primitive.*;
import de.cybine.quarkus.util.converter.*;
import de.cybine.wuf.data.organizer.*;

import java.net.*;

public class EventMapper implements EntityMapper<EventEntity, Event>
{
    @Override
    public Class<EventEntity> getEntityType( )
    {
        return EventEntity.class;
    }

    @Override
    public Class<Event> getDataType( )
    {
        return Event.class;
    }

    @Override
    public ConverterMetadataBuilder getToEntityMetadata(ConverterMetadataBuilder metadata)
    {
        return metadata.withRelation(Event.class, EventEntity.class);
    }

    @Override
    public ConverterMetadataBuilder getToDataMetadata(ConverterMetadataBuilder metadata)
    {
        return metadata.withRelation(EventEntity.class, Event.class);
    }

    @Override
    public EventEntity toEntity(Event data, ConversionHelper helper)
    {
        return EventEntity.builder()
                          .id(data.findId().map(Id::getValue).orElse(null))
                          .externalId(data.getExternalId())
                          .title(data.getTitle())
                          .startsAt(data.getStartsAt())
                          .endsAt(data.getEndsAt())
                          .link(data.getLink().map(URI::toString).orElse(null))
                          .address(data.getAddress().orElse(null))
                          .organizerId(data.getOrganizerId().map(Id::getValue).orElse(null))
                          .organizer(helper.toItem(EventOrganizer.class, EventOrganizerEntity.class)
                                           .map(data::getOrganizer))
                          .status(data.getStatus())
                          .build();
    }

    @Override
    public Event toData(EventEntity entity, ConversionHelper helper)
    {
        return Event.builder()
                    .id(entity.findId().map(EventId::of).orElse(null))
                    .externalId(entity.getExternalId())
                    .title(entity.getTitle())
                    .startsAt(entity.getStartsAt())
                    .endsAt(entity.getEndsAt())
                    .link(entity.getLink().map(URI::create).orElse(null))
                    .address(entity.getAddress().orElse(null))
                    .organizerId(entity.getOrganizerId().map(EventOrganizerId::of).orElse(null))
                    .organizer(
                            helper.toItem(EventOrganizerEntity.class, EventOrganizer.class).map(entity::getOrganizer))
                    .status(entity.getStatus())
                    .build();
    }
}
