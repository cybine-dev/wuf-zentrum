package de.cybine.wuf.config;

import io.smallrye.config.*;
import org.quartz.*;

import java.net.*;

@ConfigMapping(prefix = "quarkus.app")
public interface ApplicationConfig
{
    @WithName("base-url")
    String baseUrl( );

    @WithName("app-id")
    String appId( );

    @WithName("email")
    String email( );

    @WithName("service-name")
    String serviceName( );

    @WithName("ext-api")
    ExtApi api( );

    interface ExtApi
    {
        @WithName("name")
        @WithDefault("https://wufzentrum.de")
        URI address( );

        @WithName("fetch-interval")
        @WithDefault("0 0 * * * ?")
        CronExpression fetchInterval( );
    }
}
