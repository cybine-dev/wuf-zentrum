package de.cybine.wuf.data.event;

import de.cybine.quarkus.util.*;
import de.cybine.wuf.data.organizer.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.*;
import java.time.*;
import java.util.*;

@Data
@NoArgsConstructor
@Table(name = EventEntity_.TABLE)
@Entity(name = EventEntity_.ENTITY)
@Builder(builderClassName = "Generator")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventEntity implements Serializable, WithId<UUID>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = EventEntity_.ID_COLUMN)
    private UUID id;

    @Size(max = 500)
    @Column(name = EventEntity_.EXTERNAL_ID_COLUMN, nullable = false)
    private String externalId;

    @Size(max = 255)
    @Column(name = EventEntity_.TITLE_COLUMN, nullable = false)
    private String title;

    @Column(name = EventEntity_.STARTS_AT_COLUMN, nullable = false)
    private ZonedDateTime startsAt;

    @Column(name = EventEntity_.ENDS_AT_COLUMN, nullable = false)
    private ZonedDateTime endsAt;

    @Size(max = 500)
    @Column(name = EventEntity_.LINK_COLUMN)
    private String link;

    @Size(max = 500)
    @Column(name = EventEntity_.ADDRESS_COLUMN)
    private String address;

    @Column(name = EventEntity_.ORGANIZER_ID_COLUMN, insertable = false, updatable = false)
    private UUID organizerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EventEntity_.ORGANIZER_ID_COLUMN)
    private EventOrganizerEntity organizer;

    @Enumerated(EnumType.STRING)
    @Column(name = EventEntity_.STATUS_COLUMN, nullable = false)
    private EventStatus status;

    public Optional<String> getLink( )
    {
        return Optional.ofNullable(this.link);
    }

    public Optional<String> getAddress( )
    {
        return Optional.ofNullable(this.address);
    }

    public Optional<UUID> getOrganizerId( )
    {
        return Optional.ofNullable(this.organizerId);
    }

    public Optional<EventOrganizerEntity> getOrganizer( )
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
