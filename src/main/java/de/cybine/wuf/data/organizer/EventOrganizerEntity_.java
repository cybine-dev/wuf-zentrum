package de.cybine.wuf.data.organizer;

import de.cybine.quarkus.util.datasource.*;
import de.cybine.wuf.data.event.*;
import jakarta.persistence.metamodel.*;
import lombok.experimental.*;

import java.util.*;

@UtilityClass
@SuppressWarnings("java:S1192")
@StaticMetamodel(EventOrganizerEntity.class)
public class EventOrganizerEntity_
{
    public static final String TABLE  = "event_organizer";
    public static final String ENTITY = "Organizer";

    public static final String ID_COLUMN        = "id";
    public static final String FIRSTNAME_COLUMN = "firstname";
    public static final String LASTNAME_COLUMN  = "lastname";
    public static final String EMAIL_COLUMN     = "email";

    // @formatter:off
    public static final DatasourceField ID        =
            DatasourceField.property(EventOrganizerEntity.class, "id", UUID.class);
    public static final DatasourceField FIRSTNAME =
            DatasourceField.property(EventOrganizerEntity.class, "firstname", String.class);
    public static final DatasourceField LASTNAME  =
            DatasourceField.property(EventOrganizerEntity.class, "lastname", String.class);
    public static final DatasourceField EMAIL     =
            DatasourceField.property(EventOrganizerEntity.class, "email", String.class);
    public static final DatasourceField EVENTS    =
            DatasourceField.property(EventOrganizerEntity.class, "events", EventEntity.class);
    // @formatter:on

    public static volatile SingularAttribute<EventOrganizerEntity, UUID>    id;
    public static volatile SingularAttribute<EventOrganizerEntity, String>  firstname;
    public static volatile SingularAttribute<EventOrganizerEntity, String>  lastname;
    public static volatile SingularAttribute<EventOrganizerEntity, String>  email;
    public static volatile ListAttribute<EventOrganizerEntity, EventEntity> events;
}
