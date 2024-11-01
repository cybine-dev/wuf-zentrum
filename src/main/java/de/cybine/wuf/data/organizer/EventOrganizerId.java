package de.cybine.wuf.data.organizer;

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
@JsonDeserialize(using = EventOrganizerId.Deserializer.class)
@Schema(type = SchemaType.STRING, implementation = UUID.class)
public class EventOrganizerId implements Serializable, Id<UUID>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonValue
    @Schema(hidden = true)
    private final UUID value;

    public static EventOrganizerId create( )
    {
        return EventOrganizerId.of(UUIDv7.generate());
    }

    public static class Deserializer extends JsonDeserializer<EventOrganizerId>
    {
        @Override
        public EventOrganizerId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
        {
            String value = p.getText();
            if (value == null)
                return null;

            return EventOrganizerId.of(UUID.fromString(value));
        }
    }
}
