package perobobbot.server;

import lombok.NonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TileParser {

    public static void main(String[] args) throws IOException {
        Files.lines(Path.of("/home/perococco/tiles_list_v1.3"))
             .map(TileParser::parseOneLine)
             .flatMap(Collection::stream)
             .forEach(System.out::println);
    }

    public static List<String> parseOneLine(String line) {
        if (line.isEmpty()) {
            return List.of();
        }
        final var tokens = line.split(" ");
        return switch (tokens.length) {
            case 5 -> parseLineWith5Tokens(tokens);
            case 6 -> parseLineWith6Tokens(tokens);
            default -> List.of();
        };
    }

    private static List<String> parseLineWith5Tokens(String[] tokens) {
        final var name = tokens[0].toUpperCase();
        return List.of(name + formParameters(tokens, 1, 5));
    }

    private static @NonNull List<String> parseLineWith6Tokens(String[] tokens) {
        final int[] values = IntStream.range(1, tokens.length)
                                      .mapToObj(i -> tokens[i])
                                      .mapToInt(Integer::parseInt)
                                      .toArray();

        final var name = tokens[0].toUpperCase();
        final int nbAnim = values[4];
        return IntStream.range(0, nbAnim)
                 .mapToObj(i -> "%s_%d(%d,%d,%d,%d),".formatted(
                         name, i,
                         values[0] + i * values[2],
                         values[1],
                         values[2],
                         values[3]
                           )
                 ).collect(Collectors.toList());
    }

    private static String formParameters(String[] tokens, int first, int last) {
        return IntStream.range(first, last).mapToObj(i -> tokens[i]).collect(Collectors.joining(", ", "(", "),"));
    }

}
