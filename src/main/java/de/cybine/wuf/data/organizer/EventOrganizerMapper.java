package de.cybine.wuf.data.organizer;

import de.cybine.quarkus.data.util.primitive.*;
import de.cybine.quarkus.util.converter.*;
import de.cybine.wuf.data.event.*;

public class EventOrganizerMapper implements EntityMapper<EventOrganizerEntity, EventOrganizer>
{
    @Override
    public Class<EventOrganizerEntity> getEntityType( )
    {
        return EventOrganizerEntity.class;
    }

    @Override
    public Class<EventOrganizer> getDataType( )
    {
        return EventOrganizer.class;
    }

    @Override
    public ConverterMetadataBuilder getToEntityMetadata(ConverterMetadataBuilder metadata)
    {
        return metadata.withRelation(EventOrganizer.class, EventOrganizerEntity.class);
    }

    @Override
    public ConverterMetadataBuilder getToDataMetadata(ConverterMetadataBuilder metadata)
    {
        return metadata.withRelation(EventOrganizerEntity.class, EventOrganizer.class);
    }

    @Override
    public EventOrganizerEntity toEntity(EventOrganizer data, ConversionHelper helper)
    {
        return EventOrganizerEntity.builder()
                                   .id(data.findId().map(Id::getValue).orElse(null))
                                   .firstname(data.getFirstname().orElse(null))
                                   .lastname(data.getLastname().orElse(null))
                                   .email(data.getEmail().orElse(null))
                                   .events(helper.toList(Event.class, EventEntity.class).map(data::getEvents))
                                   .build();
    }

    @Override
    public EventOrganizer toData(EventOrganizerEntity entity, ConversionHelper helper)
    {
        return EventOrganizer.builder()
                             .id(entity.findId().map(EventOrganizerId::of).orElse(null))
                             .firstname(entity.getFirstname().orElse(null))
                             .lastname(entity.getLastname().orElse(null))
                             .email(entity.getEmail().orElse(null))
                             .events(helper.toList(EventEntity.class, Event.class).map(entity::getEvents))
                             .build();
    }
}
