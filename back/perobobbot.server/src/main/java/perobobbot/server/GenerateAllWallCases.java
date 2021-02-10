package perobobbot.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GenerateAllWallCases {


    public static void main(String[] args) throws IOException {
        Files.writeString(Path.of("/home/perococco/dev/stream/bot/_doc/wall_cases.txt"),
                          IntStream.range(0, 256)
                                   .mapToObj(GenerateAllWallCases::printCase)
                                   .flatMap(l -> l.stream())
                                   .collect(Collectors.joining("\n")));
    }

    private static List<String> printCase(int index) {
        final IntFunction<String> cf = f -> getCharAt(index, f);

        final var line1 = IntStream.range(0, 3).mapToObj(cf).collect(Collectors.joining("|"));
        final var line2 = cf.apply(3) + "|W|" + cf.apply(4);
        final var line3 = IntStream.range(5, 8).mapToObj(cf).collect(Collectors.joining("|"));


        return List.of("" + index, line1, "-+-+-", line2, "-+-+-", line3);
    }

    private static String getCharAt(int index, int weight) {
        return (index & (1 << weight)) != 0 ? "F" : " ";
    }
}
