package perobobbot.twitch.client.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import perobobbot.twitch.client.api.deser.TwitchApiSubModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AbstractDesetTest {


    protected ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        this.objectMapper = new ObjectMapper();
        TwitchApiSubModule.provider().getJsonModules().forEach(m -> objectMapper.registerModule(m));
    }



    protected static Stream<String> readContent(String resourceNamePrefix) {
        return IntStream.iterate(1,i -> i+1)
                        .mapToObj(i -> resourceNamePrefix+"_"+i+".json")
                        .map(DeserCustomRewardsTest.class::getResource)
                        .takeWhile(Objects::nonNull)
                        .map(url -> readContent(url));
    }


    private static String readContent(URL url) {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return r.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
