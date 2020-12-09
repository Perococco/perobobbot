package perobobbot.puckwar.game;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.lang.fp.Function0;
import perobobbot.lang.fp.Function1;
import perobobbot.rendering.*;

import java.awt.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class HighScoreTable implements Renderable {

    public static final Color BACKGROUND_COLOR = new Color(255, 255, 255, 92);
    public static final int BACKGROUND_MARGIN = 20;

    public static @NonNull HighScoreTable lowerIsBetter(int tableLength) {
        return new HighScoreTable(tableLength, Score.LOWER_IS_FIRST);
    }

    public static @NonNull HighScoreTable higherIsBetter(int tableLength) {
        return new HighScoreTable(tableLength, Score.HIGHER_IS_FIRST);
    }

    private final static Font FONT = new Font("Monospaced", Font.PLAIN, 36);

    private final int tableLength;

    private final @NonNull Comparator<Score> scoreComparator;

    private @NonNull ImmutableList<Score> scores = ImmutableList.of();

    @Getter
    @Setter
    private Positioning positioning = new Positioning(0, 0, HAlignment.LEFT, VAlignment.TOP);

    public HighScoreTable(int tableLength, Comparator<Score> scoreComparator) {
        this.tableLength = tableLength;
        this.scoreComparator = scoreComparator;
    }

    public @NonNull Optional<Score> getBestScore() {
        final ImmutableList<Score> scores = this.scores;
        if (scores.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(scores.get(0));
        }
    }


    public <T> void fillTable(@NonNull Collection<T> items,
                              @NonNull Function1<? super T, ? extends Score> scoreExtractor) {
        fillTable(() -> items.stream().map(scoreExtractor));
    }

    public <T> void fillTable(@NonNull Function0<? extends Stream<? extends Score>> scoreStream) {
        this.scores = scoreStream.f()
                                 .sorted(scoreComparator)
                                 .limit(tableLength)
                                 .collect(ImmutableList.toImmutableList());
    }

    @Override
    public void drawWith(@NonNull Renderer renderer) {
        renderer.withPrivateContext(r -> {
            final BlockBuilder blockBuilder = r.blockBuilder()
                                               .setBackgroundColor(BACKGROUND_COLOR)
                                               .setBackgroundMargin(BACKGROUND_MARGIN)
                                               .setFont(FONT)
                                               .setColor(Color.WHITE)
                                               .addString(Messager.getHighScoreTableTitle(), HAlignment.MIDDLE)
                                               .addSeparator();
            this.scores.stream()
                       .map(Score::getScoreText)
                       .forEach(t -> blockBuilder.addString(t, HAlignment.LEFT));

            final Block block = blockBuilder.build();
            block.draw(positioning);
        });
    }

    public boolean isEmpty() {
        return scores.isEmpty();
    }
}
