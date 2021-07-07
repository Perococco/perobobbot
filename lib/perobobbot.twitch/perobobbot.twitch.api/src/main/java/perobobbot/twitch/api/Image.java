package perobobbot.twitch.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.NonNull;
import lombok.Value;

import java.net.URL;

@Value
public class Image {
    @JsonAlias("url_1x")
    @NonNull URL url1x;
    @JsonAlias("url_2x")
    @NonNull URL url2x;
    @JsonAlias("url_4x")
    @NonNull URL url4x;
}
