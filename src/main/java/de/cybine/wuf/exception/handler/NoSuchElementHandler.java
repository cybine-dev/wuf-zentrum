package de.cybine.wuf.exception.handler;

import de.cybine.quarkus.api.response.*;
import de.cybine.quarkus.util.api.*;
import org.jboss.resteasy.reactive.*;
import org.jboss.resteasy.reactive.server.*;

import java.util.*;

@SuppressWarnings("unused")
public class NoSuchElementHandler
{
    @ServerExceptionMapper
    public RestResponse<ApiResponse<Void>> toResponse(NoSuchElementException exception)
    {
        return ApiResponse.<Void>builder()
                          .statusCode(RestResponse.Status.NOT_FOUND.getStatusCode())
                          .error(ApiError.builder().code("element-not-found").message(exception.getMessage()).build())
                          .build()
                          .transform(ApiQueryHelper::createResponse);
    }
}
