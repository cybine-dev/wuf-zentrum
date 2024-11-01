package de.cybine.wuf.data.event;

import lombok.*;

import java.time.*;
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

        if (!Objects.equals(this.previous.getStartsAt().withZoneSameInstant(ZoneId.systemDefault()), this.next.getStartsAt().withZoneSameInstant(ZoneId.systemDefault())))
            return true;

        if (!Objects.equals(this.previous.getEndsAt().withZoneSameInstant(ZoneId.systemDefault()), this.next.getEndsAt().withZoneSameInstant(ZoneId.systemDefault())))
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
