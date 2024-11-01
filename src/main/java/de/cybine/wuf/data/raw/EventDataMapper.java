package de.cybine.wuf.data.raw;

import de.cybine.quarkus.data.util.primitive.*;
import de.cybine.quarkus.util.converter.*;

public class EventDataMapper implements EntityMapper<EventDataEntity, EventData>
{
    @Override
    public Class<EventDataEntity> getEntityType( )
    {
        return EventDataEntity.class;
    }

    @Override
    public Class<EventData> getDataType( )
    {
        return EventData.class;
    }

    @Override
    public EventDataEntity toEntity(EventData data, ConversionHelper helper)
    {
        return EventDataEntity.builder()
                              .id(data.findId().map(Id::getValue).orElse(null))
                              .createdAt(data.getCreatedAt())
                              .type(data.getType())
                              .externalId(data.getExternalId())
                              .data(data.getData())
                              .build();
    }

    @Override
    public EventData toData(EventDataEntity entity, ConversionHelper helper)
    {
        return EventData.builder()
                        .id(entity.findId().map(EventDataId::of).orElse(null))
                        .createdAt(entity.getCreatedAt())
                        .type(entity.getType())
                        .externalId(entity.getExternalId())
                        .data(entity.getData())
                        .build();
    }
}
