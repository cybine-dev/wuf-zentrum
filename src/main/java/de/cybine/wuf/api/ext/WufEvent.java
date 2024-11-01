package de.cybine.wuf.api.ext;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.extern.jackson.*;

import java.net.*;
import java.time.*;

@Data
@Jacksonized
@Builder(builderMethodName = "Generator")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WufEvent
{
    @JsonProperty("id")
    private final Integer id;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("allDay")
    private final boolean allDay;

    @JsonProperty("start")
    private final ZonedDateTime startsAt;

    @JsonProperty("end")
    private final ZonedDateTime endsAt;

    @JsonProperty("url")
    @EqualsAndHashCode.Include
    private final URI link;

    @JsonProperty("backgroundColor")
    private final String backgroundColor;

    @JsonProperty("borderColor")
    private final String borderColor;

    public String getTitle( )
    {
        return this.title.replace("__D", "") // weekly
                         .replace("__R", "") // semi-regular
                         .replace("&#8211;", "-")
                         .replace("&#038;", "&")
                         .replace("&#8220;", "\"")
                         .replace("&#8222;", "\"")
                         .replace("&#8230;", "...");
    }

    public String getSlag( )
    {
        return this.link.getPath().replace("/index.php/event/", "").replace("/", "");
    }
}