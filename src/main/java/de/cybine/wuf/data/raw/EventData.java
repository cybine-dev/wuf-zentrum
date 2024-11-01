package de.cybine.wuf.data.raw;

import com.fasterxml.jackson.annotation.*;
import de.cybine.quarkus.util.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.io.*;
import java.time.*;
import java.util.*;

@Data
@Jacksonized
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventData implements Serializable, WithId<EventDataId>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private final EventDataId id;

    @JsonProperty("created_at")
    private final ZonedDateTime createdAt;

    @JsonProperty("type")
    private final String type;

    @JsonProperty("external_id")
    private final String externalId;

    @JsonProperty("data")
    private final String data;

    @Override
    public boolean equals(Object other)
    {
        if (other == null)
            return false;

        if (this.getClass() != other.getClass())
            return false;

        WithId<?> that = ((WithId<?>) other);
        if (this.findId().isEmpty() || that.findId().isEmpty())
            return false;

        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode( )
    {
        return this.findId().map(Object::hashCode).orElse(0);
    }
}
