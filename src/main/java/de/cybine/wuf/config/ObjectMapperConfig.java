package de.cybine.wuf.config;

import com.fasterxml.jackson.annotation.JsonInclude.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jdk8.*;
import io.quarkus.jackson.*;
import io.quarkus.runtime.*;
import jakarta.enterprise.context.*;

@Startup
@Dependent
public class ObjectMapperConfig implements ObjectMapperCustomizer
{
    @Override
    public void customize(ObjectMapper mapper)
    {
        mapper.registerModule(new Jdk8Module());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(Include.NON_ABSENT);
    }
}
