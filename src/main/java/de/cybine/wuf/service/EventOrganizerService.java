package de.cybine.wuf.service;

import de.cybine.quarkus.util.api.*;
import de.cybine.quarkus.util.api.query.*;
import de.cybine.wuf.data.organizer.*;
import io.quarkus.runtime.*;
import jakarta.inject.*;
import lombok.*;

import java.util.*;

@Startup
@Singleton
@RequiredArgsConstructor
public class EventOrganizerService
{
    // @formatter:off
    private final GenericApiQueryService<EventOrganizerEntity, EventOrganizer> service =
            GenericApiQueryService.forType(EventOrganizerEntity.class, EventOrganizer.class);
    // @formatter:on

    public List<EventOrganizer> fetch(ApiQuery query)
    {
        return this.service.fetch(query);
    }

    public Optional<EventOrganizer> fetchSingle(ApiQuery query)
    {
        return this.service.fetchSingle(query);
    }

    public List<Map<String, Object>> fetchOptions(ApiQuery query)
    {
        return this.service.fetchOptions(query);
    }

    public List<ApiCountInfo> fetchTotal(ApiQuery query)
    {
        return this.service.fetchTotal(query);
    }
}
