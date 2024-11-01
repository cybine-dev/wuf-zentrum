package de.cybine.wuf.data.event;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import de.cybine.quarkus.data.util.*;
import de.cybine.quarkus.data.util.primitive.*;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.enums.*;
import org.eclipse.microprofile.openapi.annotations.media.*;

import java.io.*;
import java.util.*;

@Data
@RequiredArgsConstructor(staticName = "of")
@JsonDeserialize(using = EventId.Deserializer.class)
@Schema(type = SchemaType.STRING, implementation = UUID.class)
public class EventId implements Serializable, Id<UUID>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonValue
    @Schema(hidden = true)
    private final UUID value;

    public static EventId create( )
    {
        return EventId.of(UUIDv7.generate());
    }

    public static class Deserializer extends JsonDeserializer<EventId>
    {
        @Override
        public EventId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
        {
            String value = p.getText();
            if (value == null)
                return null;

            return EventId.of(UUID.fromString(value));
        }
    }
}
