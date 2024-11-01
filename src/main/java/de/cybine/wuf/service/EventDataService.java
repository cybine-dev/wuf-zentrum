package de.cybine.wuf.service;

import de.cybine.quarkus.util.api.*;
import de.cybine.quarkus.util.api.query.*;
import de.cybine.quarkus.util.datasource.*;
import de.cybine.wuf.data.raw.*;
import io.quarkus.runtime.*;
import jakarta.inject.*;
import lombok.*;

import java.util.*;

@Startup
@Singleton
@RequiredArgsConstructor
public class EventDataService
{
    // @formatter:off
    private final GenericApiQueryService<EventDataEntity, EventData> service =
            GenericApiQueryService.forType(EventDataEntity.class, EventData.class);
    // @formatter:on

    public List<EventData> fetch(ApiQuery query)
    {
        return this.service.fetch(query);
    }

    public Optional<EventData> fetchCurrentData(String type, String externalId)
    {
        DatasourceConditionDetail<String> hasEventType = DatasourceHelper.isEqual(EventDataEntity_.TYPE, type);
        DatasourceConditionDetail<String> hasExternalId = DatasourceHelper.isEqual(EventDataEntity_.EXTERNAL_ID,
                externalId);

        DatasourceConditionInfo condition = DatasourceHelper.and(hasEventType);
        if (externalId != null)
            condition = DatasourceHelper.and(hasEventType, hasExternalId);

        DatasourceQuery query = DatasourceQuery.builder()
                                               .order(DatasourceHelper.desc(EventDataEntity_.CREATED_AT))
                                               .condition(condition)
                                               .build();

        return this.service.fetchSingle(query);
    }

    public Optional<EventData> fetchSingle(ApiQuery query)
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
