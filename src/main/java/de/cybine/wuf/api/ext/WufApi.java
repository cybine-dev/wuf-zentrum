package de.cybine.wuf.api.ext;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.*;

@Path("/wp-content")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface WufApi
{
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/themes/born-to-give/framework/json-feed.php")
    List<WufEvent> fetchEvents(@FormParam("month_event") String eventMonth);
}
