package de.cybine.wuf.data.event;

import com.fasterxml.jackson.annotation.*;
import de.cybine.quarkus.util.*;
import de.cybine.wuf.data.organizer.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.io.*;
import java.net.*;
import java.time.*;
import java.util.*;

@Data
@Jacksonized
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Event implements Serializable, WithId<EventId>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private final EventId id;

    @JsonProperty("external_id")
    private final String externalId;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("starts_at")
    private final ZonedDateTime startsAt;

    @JsonProperty("ends_at")
    private final ZonedDateTime endsAt;

    @JsonProperty("link")
    private final URI link;

    @JsonProperty("address")
    private final String address;

    @JsonProperty("organizer_id")
    private final EventOrganizerId organizerId;

    @JsonProperty("organizer")
    private final EventOrganizer organizer;

    @JsonProperty("status")
    private final EventStatus status;

    public Optional<URI> getLink( )
    {
        return Optional.ofNullable(this.link);
    }

    public Optional<String> getAddress( )
    {
        return Optional.ofNullable(this.address);
    }

    public Optional<EventOrganizerId> getOrganizerId( )
    {
        return Optional.ofNullable(this.organizerId);
    }

    public Optional<EventOrganizer> getOrganizer( )
    {
        return Optional.ofNullable(this.organizer);
    }

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
