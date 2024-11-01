package de.cybine.wuf.data.organizer;

import com.fasterxml.jackson.annotation.*;
import de.cybine.quarkus.util.*;
import de.cybine.wuf.data.event.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.io.*;
import java.util.*;

@Data
@Jacksonized
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventOrganizer implements Serializable, WithId<EventOrganizerId>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private final EventOrganizerId id;

    @JsonProperty("firstname")
    private final String firstname;

    @JsonProperty("lastname")
    private final String lastname;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("events")
    private final List<Event> events;

    public Optional<String> getFirstname( )
    {
        return Optional.ofNullable(this.firstname);
    }

    public Optional<String> getLastname( )
    {
        return Optional.ofNullable(this.lastname);
    }

    public Optional<String> getEmail( )
    {
        return Optional.ofNullable(this.email);
    }

    public Optional<List<Event>> getEvents( )
    {
        return Optional.ofNullable(this.events);
    }

    @JsonProperty("event_ids")
    public Optional<List<EventId>> getEventIds( )
    {
        return this.getEvents().map(item -> item.stream().map(WithId::getId).toList());
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
