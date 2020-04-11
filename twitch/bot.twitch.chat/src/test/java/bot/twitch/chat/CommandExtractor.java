package bot.twitch.chat;

import lombok.NonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author perococco
 **/
public class CommandExtractor {

    public static void main(String[] args) throws IOException {
        new CommandExtractor().allCommands().forEach(System.out::println);
    }

    public Set<String> allCommands() throws IOException {
        return Files.lines(Path.of("/home/perococco/chat.txt"))
                    .map(this::extract)
                    .collect(Collectors.toCollection(TreeSet::new));
    }

    public String extract(@NonNull String line) {
        String working = line;
        if (working.startsWith("@")) {
            working = working.substring(working.indexOf(" ")+1);
        }
        if (working.startsWith(":")) {
            working = working.substring(working.indexOf(" ")+1);
        }

        return working.substring(0,working.indexOf(" "));
    }
}
