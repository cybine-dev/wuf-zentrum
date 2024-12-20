package de.cybine.wuf.api.ext.converter;

import de.cybine.quarkus.util.converter.*;
import de.cybine.wuf.api.ext.*;
import de.cybine.wuf.data.event.*;
import lombok.extern.slf4j.*;

import java.time.*;

@Slf4j
public class WufEventConverter implements Converter<WufEvent, Event>
{
    private static final ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");

    @Override
    public Class<WufEvent> getInputType( )
    {
        return WufEvent.class;
    }

    @Override
    public Class<Event> getOutputType( )
    {
        return Event.class;
    }

    @Override
    public Event convert(WufEvent input, ConversionHelper helper)
    {
        ZonedDateTime endsAt = input.getEndsAt();
        ZonedDateTime startsAt = input.getStartsAt().withZoneSameInstant(ZONE_ID);
        if (endsAt.isBefore(input.getStartsAt()))
        {
            try
            {
                endsAt = endsAt.withZoneSameInstant(ZONE_ID)
                               .with(startsAt.toLocalDate());
            }
            catch (DateTimeException exception)
            {
                log.warn("Could not convert event ({}): Invalid end-timestamp. [{}]", input.getLink(),
                        exception.getMessage());

                return null;
            }
        }

        while (endsAt.isBefore(startsAt))
            endsAt = endsAt.plusDays(1);

        return Event.builder()
                    .title(input.getTitle())
                    .startsAt(startsAt)
                    .endsAt(endsAt)
                    .externalId(input.getLink().toString())
                    .link(input.getLink())
                    .status(EventStatus.ACTIVE)
                    .build();
    }
}
