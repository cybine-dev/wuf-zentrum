package de.cybine.wuf.exception.handler;

import de.cybine.quarkus.api.response.*;
import de.cybine.quarkus.util.api.*;
import org.hibernate.exception.*;
import org.jboss.resteasy.reactive.*;
import org.jboss.resteasy.reactive.server.*;

@SuppressWarnings("unused")
public class ConstraintViolationHandler
{
    @ServerExceptionMapper
    public RestResponse<ApiResponse<Void>> toResponse(ConstraintViolationException exception)
    {
        return ApiResponse.<Void>builder()
                          .statusCode(RestResponse.Status.CONFLICT.getStatusCode())
                          .error(ApiError.builder()
                                         .code("db-constraint-violation")
                                         .message(exception.getCause().getMessage())
                                         .build())
                          .build()
                          .transform(ApiQueryHelper::createResponse);
    }
}
