package de.cybine.wuf.api.calendar;

import de.cybine.wuf.service.*;
import jakarta.ws.rs.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.jboss.resteasy.reactive.*;

@Slf4j
@RequiredArgsConstructor
public class CalendarResource implements CalendarApi
{
    private final CalendarService service;

    @Override
    @Produces("text/calendar")
    public RestResponse<String> fetchCalendar(boolean download)
    {
        RestResponse.ResponseBuilder<String> response = RestResponse.ResponseBuilder.ok(this.service.getCalendar());
        if (download)
            response.header("Content-Disposition", "attachment; filename=wuf-zentrum.ics");

        return response.build();
    }
}
