package perobobbot.template;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class Templates {

    public static Templates read() throws IOException {
        final var structureUrl = Templates.class.getResource("/template/structure.txt");
        final var builder = ImmutableList.<StructureEntry>builder();
        try (BufferedReader is = new BufferedReader(new InputStreamReader(structureUrl.openStream()))) {
            String line;
            while ((line = is.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    builder.add(StructureEntryParser.parse(line));
                }
            }
        }
        return new Templates(builder.build());
    }

    @NonNull ImmutableList<StructureEntry> entries;

    public void forEach(@NonNull String type, @NonNull Consumer<? super StructureEntry> action) {
        entries.stream().filter(e -> e.isIncludedFor(type)).forEach(action);
    }

}
