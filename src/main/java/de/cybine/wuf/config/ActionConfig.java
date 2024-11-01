package de.cybine.wuf.config;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.*;
import de.cybine.quarkus.util.action.data.*;
import de.cybine.quarkus.util.action.stateful.*;
import de.cybine.wuf.data.event.*;
import de.cybine.wuf.service.action.*;
import de.cybine.wuf.service.calendar.*;
import io.quarkus.runtime.*;
import jakarta.annotation.*;
import jakarta.inject.*;
import lombok.*;

import static de.cybine.quarkus.util.action.ActionProcessorBuilder.*;
import static de.cybine.quarkus.util.action.data.ActionProcessorMetadata.*;
import static de.cybine.wuf.service.action.ActionService.*;

@Startup
@Singleton
@RequiredArgsConstructor
public class ActionConfig
{
    public static final String WUF_NAMESPACE     = "wuf-zentrum";
    public static final String CALENDAR_CATEGORY = "calendar";

    public static final String CALENDAR_SYNC_NAME    = "event-sync";
    public static final String REGISTER_EVENT_ACTION = "register-event";
    public static final String UPDATE_EVENT_ACTION   = "update-event";
    public static final String REMOVE_EVENT_ACTION   = "remove-event";

    private final ActionService service;

    private final ObjectMapper           objectMapper;
    private final ActionDataTypeRegistry typeRegistry;

    @PostConstruct
    void setup( )
    {
        TypeFactory typeFactory = this.objectMapper.getTypeFactory();
        this.typeRegistry.registerType("de.cybine.wuf.relay:event:v1", typeFactory.constructType(Event.class));
        this.typeRegistry.registerType("de.cybine.wuf.relay:event-diff:v1", typeFactory.constructType(EventDiff.class));

        // @formatter:off
        WorkflowBuilder.create(WUF_NAMESPACE, CALENDAR_CATEGORY, CALENDAR_SYNC_NAME)
                       .type(WorkflowType.ACTION)
                       .with(on(REGISTER_EVENT_ACTION).from(ANY).apply(EventActionProcessor::register))
                       .with(on(UPDATE_EVENT_ACTION).from(ANY).apply(EventActionProcessor::update).when(EventActionProcessor::updateWhen))
                       .with(on(REMOVE_EVENT_ACTION).from(ANY).apply(EventActionProcessor::remove))
                       .with(on(TERMINATED_STATE).from(ANY))
                       .apply(this.service);
        // @formatter:on
    }
}
