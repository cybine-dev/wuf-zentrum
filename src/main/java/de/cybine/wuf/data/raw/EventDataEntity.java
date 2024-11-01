package de.cybine.wuf.data.raw;

import de.cybine.quarkus.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.*;
import java.time.*;
import java.util.*;

@Data
@NoArgsConstructor
@Builder(builderClassName = "Generator")
@Table(name = EventDataEntity_.TABLE)
@Entity(name = EventDataEntity_.ENTITY)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventDataEntity implements Serializable, WithId<UUID>
{
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = EventDataEntity_.ID_COLUMN)
    private UUID id;

    @Column(name = EventDataEntity_.CREATED_AT_COLUMN, nullable = false)
    private ZonedDateTime createdAt;

    @Size(max = 63)
    @Column(name = EventDataEntity_.TYPE_COLUMN, nullable = false)
    private String type;

    @Size(max = 255)
    @Column(name = EventDataEntity_.EXTERNAL_ID_COLUMN, nullable = false)
    private String externalId;

    @Column(name = EventDataEntity_.DATA_COLUMN, nullable = false)
    private String data;

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
