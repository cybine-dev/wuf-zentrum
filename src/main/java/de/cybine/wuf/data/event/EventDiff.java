package de.cybine.wuf.data.event;

import lombok.*;

import java.util.*;

@Data
@AllArgsConstructor(staticName = "of")
public class EventDiff
{
    private final Event previous;
    private final Event next;

    @SuppressWarnings("RedundantIfStatement")
    public boolean hasDiff( )
    {
        if (!Objects.equals(this.previous.getTitle(), this.next.getTitle()))
            return true;

        if (!Objects.equals(this.previous.getStartsAt(), this.next.getStartsAt()))
            return true;

        if (!Objects.equals(this.previous.getEndsAt(), this.next.getEndsAt()))
            return true;

        if (!Objects.equals(this.previous.getLink().orElse(null), this.next.getLink().orElse(null)))
            return true;

        if (!Objects.equals(this.previous.getAddress().orElse(null), this.next.getAddress().orElse(null)))
            return true;

        if (!Objects.equals(this.previous.getOrganizerId().orElse(null), this.next.getOrganizerId().orElse(null)))
            return true;

        if (!Objects.equals(this.previous.getStatus(), this.next.getStatus()))
            return true;

        return false;
    }
}
