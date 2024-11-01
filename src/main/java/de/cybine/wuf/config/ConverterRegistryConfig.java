package de.cybine.wuf.config;

import de.cybine.quarkus.util.converter.*;
import de.cybine.wuf.api.ext.converter.*;
import de.cybine.wuf.data.action.context.*;
import de.cybine.wuf.data.action.process.*;
import de.cybine.wuf.data.event.*;
import de.cybine.wuf.data.organizer.*;
import de.cybine.wuf.data.raw.*;
import io.quarkus.runtime.*;
import jakarta.annotation.*;
import jakarta.enterprise.context.*;
import lombok.*;

@Startup
@Dependent
@RequiredArgsConstructor
public class ConverterRegistryConfig
{
    private final ConverterRegistry registry;

    @PostConstruct
    void setup( )
    {
        this.registry.addEntityMapper(new ActionContextMapper());
        this.registry.addEntityMapper(new ActionProcessMapper());

        this.registry.addEntityMapper(new EventMapper());
        this.registry.addEntityMapper(new EventDataMapper());
        this.registry.addEntityMapper(new EventOrganizerMapper());

        this.registry.addConverter(new WufEventConverter());
    }
}
