package de.cybine.wuf.api.calendar;

import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.*;

@Path("/api/v1/calendar")
public interface CalendarApi
{
    @GET
    @Produces("text/calendar")
    RestResponse<String> fetchCalendar(@QueryParam("download") boolean download);
}
