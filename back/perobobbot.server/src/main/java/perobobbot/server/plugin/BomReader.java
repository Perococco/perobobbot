package perobobbot.server.plugin;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@RequiredArgsConstructor
public class BomReader {

    public static @NonNull Bom read(@NonNull URL dependencyList) throws IOException {
        return new BomReader(dependencyList)._read();
    }

    public static @NonNull Bom read() throws IOException {
        return read(BomReader.class.getResource("/dependencies.txt"));
    }

    private final @NonNull URL dependencyList;

    private @NonNull Bom _read() throws IOException {
        final var builder = ImmutableList.<Dependency>builder();
        try (var is = new BufferedReader(new InputStreamReader(dependencyList.openStream()))) {
            String line = null;
            while((line = is.readLine())!=null) {
                if (line.isBlank() || line.contains("The following files have been resolved")) {
                    continue;
                }
                final String[] tokens = line.trim().split(":");
                if (tokens.length<5) {
                    continue;
                }
                builder.add(new Dependency(tokens[0],tokens[1],tokens[3]));
            }
        }
        return new Bom(builder.build());
    }
}
