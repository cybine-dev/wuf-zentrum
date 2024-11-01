package de.cybine.wuf.data.organizer;

import de.cybine.quarkus.util.*;
import de.cybine.wuf.data.event.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.*;
import java.util.*;

@Data
@NoArgsConstructor
@Builder(builderClassName = "Generator")
@Table(name = EventOrganizerEntity_.TABLE)
@Entity(name = EventOrganizerEntity_.ENTITY)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventOrganizerEntity implements Serializable, WithId<UUID>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = EventOrganizerEntity_.ID_COLUMN)
    private UUID id;

    @Column(name = EventOrganizerEntity_.FIRSTNAME_COLUMN)
    private String firstname;

    @Column(name = EventOrganizerEntity_.LASTNAME_COLUMN)
    private String lastname;

    @Column(name = EventOrganizerEntity_.EMAIL_COLUMN)
    private String email;

    @OneToMany(mappedBy = EventEntity_.ORGANIZER_RELATION)
    private List<EventEntity> events;

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

    public Optional<List<EventEntity>> getEvents( )
    {
        return Optional.ofNullable(this.events);
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
