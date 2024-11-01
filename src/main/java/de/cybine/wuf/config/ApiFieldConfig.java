package de.cybine.wuf.config;

import de.cybine.quarkus.util.api.*;
import de.cybine.wuf.data.event.*;
import de.cybine.wuf.data.organizer.*;
import io.quarkus.runtime.*;
import jakarta.annotation.*;
import jakarta.inject.*;
import lombok.*;

@Startup
@Singleton
@RequiredArgsConstructor
public class ApiFieldConfig
{
    private final ApiFieldResolver fieldResolver;

    @PostConstruct
    void setup( )
    {
        this.registerEvent();
        this.registerEventOrganizer();
    }

    private void registerEvent( )
    {
        this.fieldResolver.registerType(Event.class)
                          .withField("id", EventEntity_.ID)
                          .withField("external_id", EventEntity_.EXTERNAL_ID)
                          .withField("title", EventEntity_.TITLE)
                          .withField("starts_at", EventEntity_.STARTS_AT)
                          .withField("ends_at", EventEntity_.ENDS_AT)
                          .withField("link", EventEntity_.LINK)
                          .withField("address", EventEntity_.ADDRESS)
                          .withField("organizer_id", EventEntity_.ORGANIZER_ID)
                          .withRelation("organizer", EventEntity_.ORGANIZER, EventOrganizer.class);
    }

    private void registerEventOrganizer( )
    {
        this.fieldResolver.registerType(EventOrganizer.class)
                          .withField("id", EventOrganizerEntity_.ID)
                          .withField("firstname", EventOrganizerEntity_.FIRSTNAME)
                          .withField("lastname", EventOrganizerEntity_.LASTNAME)
                          .withField("email", EventOrganizerEntity_.EMAIL)
                          .withRelation("events", EventOrganizerEntity_.EVENTS, EventOrganizer.class);
    }
}
