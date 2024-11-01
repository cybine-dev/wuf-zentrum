package de.cybine.wuf.data.raw;

import de.cybine.quarkus.util.datasource.*;
import jakarta.persistence.metamodel.*;
import lombok.experimental.*;

import java.time.*;
import java.util.*;

@UtilityClass
@SuppressWarnings("java:S1192")
@StaticMetamodel(EventDataEntity.class)
public class EventDataEntity_
{
    public static final String TABLE  = "event_data";
    public static final String ENTITY = "EventData";

    public static final String ID_COLUMN          = "id";
    public static final String CREATED_AT_COLUMN  = "created_at";
    public static final String TYPE_COLUMN        = "type";
    public static final String EXTERNAL_ID_COLUMN = "external_id";
    public static final String DATA_COLUMN        = "data";

    // @formatter:off
    public static final DatasourceField ID          =
            DatasourceField.property(EventDataEntity.class, "id", UUID.class);
    public static final DatasourceField CREATED_AT  =
            DatasourceField.property(EventDataEntity.class, "createdAt", ZonedDateTime.class);
    public static final DatasourceField TYPE        =
            DatasourceField.property(EventDataEntity.class, "type", String.class);
    public static final DatasourceField EXTERNAL_ID =
            DatasourceField.property(EventDataEntity.class, "externalId", String.class);
    public static final DatasourceField DATA        =
            DatasourceField.property(EventDataEntity.class, "data", String.class);
    // @formatter:on

    public static volatile SingularAttribute<EventDataEntity, UUID>          id;
    public static volatile SingularAttribute<EventDataEntity, ZonedDateTime> createdAt;
    public static volatile SingularAttribute<EventDataEntity, String>        type;
    public static volatile SingularAttribute<EventDataEntity, String>        externalId;
    public static volatile SingularAttribute<EventDataEntity, String>        data;
}
