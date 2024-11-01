package de.cybine.wuf.data.event;

import de.cybine.quarkus.util.datasource.*;
import de.cybine.wuf.data.organizer.*;
import jakarta.persistence.metamodel.*;
import lombok.experimental.*;

import java.time.*;
import java.util.*;

@UtilityClass
@SuppressWarnings("java:S1192")
@StaticMetamodel(EventEntity.class)
public class EventEntity_
{
    public static final String TABLE  = "event";
    public static final String ENTITY = "Event";

    public static final String ID_COLUMN           = "id";
    public static final String EXTERNAL_ID_COLUMN  = "external_id";
    public static final String TITLE_COLUMN        = "title";
    public static final String STARTS_AT_COLUMN    = "starts_at";
    public static final String ENDS_AT_COLUMN      = "ends_at";
    public static final String LINK_COLUMN         = "link";
    public static final String ADDRESS_COLUMN      = "address";
    public static final String ORGANIZER_ID_COLUMN = "organizer_id";
    public static final String STATUS_COLUMN       = "status";

    // @formatter:off
    public static final DatasourceField ID           =
            DatasourceField.property(EventEntity.class, "id", UUID.class);
    public static final DatasourceField EXTERNAL_ID  =
            DatasourceField.property(EventEntity.class, "externalId", String.class);
    public static final DatasourceField TITLE        =
            DatasourceField.property(EventEntity.class, "title", String.class);
    public static final DatasourceField STARTS_AT    =
            DatasourceField.property(EventEntity.class, "startsAt", ZonedDateTime.class);
    public static final DatasourceField ENDS_AT      =
            DatasourceField.property(EventEntity.class, "endsAt", ZonedDateTime.class);
    public static final DatasourceField LINK         =
            DatasourceField.property(EventEntity.class, "link", String.class);
    public static final DatasourceField ADDRESS      =
            DatasourceField.property(EventEntity.class, "address", String.class);
    public static final DatasourceField ORGANIZER_ID =
            DatasourceField.property(EventEntity.class, "organizerId", UUID.class);
    public static final DatasourceField ORGANIZER    =
            DatasourceField.property(EventEntity.class, "organizer", EventOrganizerEntity.class);
    public static final DatasourceField STATUS       =
            DatasourceField.property(EventEntity.class, "status", EventStatus.class);
    // @formatter:on

    public static final String ORGANIZER_RELATION = "organizer";

    public static volatile SingularAttribute<EventEntity, UUID>                 id;
    public static volatile SingularAttribute<EventEntity, String>               externalId;
    public static volatile SingularAttribute<EventEntity, String>               title;
    public static volatile SingularAttribute<EventEntity, ZonedDateTime>        startsAt;
    public static volatile SingularAttribute<EventEntity, ZonedDateTime>        endsAt;
    public static volatile SingularAttribute<EventEntity, String>               link;
    public static volatile SingularAttribute<EventEntity, String>               address;
    public static volatile SingularAttribute<EventEntity, UUID>                 organizerId;
    public static volatile SingularAttribute<EventEntity, EventOrganizerEntity> organizer;
    public static volatile SingularAttribute<EventEntity, EventStatus>          status;
}
