package perobobbot.puckwar.game;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.common.lang.fp.Function1;
import perobobbot.overlay.api.OverlayRenderer;
import perobobbot.overlay.api.VAlignment;

import java.awt.*;
import java.util.Collection;
import java.util.Comparator;

public class HighScoreTable {

    public static @NonNull HighScoreTable lowerIsBetter(int tableLength) {
        return new HighScoreTable(tableLength,Score.LOWER_IS_FIRST);
    }

    public static @NonNull HighScoreTable higherIsBetter(int tableLength) {
        return new HighScoreTable(tableLength,Score.HIGHER_IS_FIRST);
    }

    private final static Font FONT = new Font("Monospaced", Font.PLAIN, 36);

    private final int tableLength;

    private final @NonNull Comparator<Score> scoreComparator;

    private @NonNull ImmutableList<Score> scores = ImmutableList.of();

    public HighScoreTable(int tableLength, Comparator<Score> scoreComparator) {
        this.tableLength = tableLength;
        this.scoreComparator = scoreComparator;
    }

    public <T> void fillTable(@NonNull Collection<T> items,
                              @NonNull Function1<? super T, ? extends Score> scoreExtractor) {
        this.scores = items.stream()
                           .map(scoreExtractor)
                           .sorted(scoreComparator)
                           .limit(tableLength)
                           .collect(ImmutableList.toImmutableList());
    }

    public void drawWith(@NonNull OverlayRenderer overlayRenderer) {
        overlayRenderer.withPrivateContext(r -> {
            overlayRenderer.setFont(FONT);
            final var lineHeight = overlayRenderer.getTextLineHeight();
            final ImmutableList<Score> scores = this.scores;
            for (int i = 0; i < scores.size(); i++) {
                final Score score = scores.get(i);
                overlayRenderer.drawString(score.getText(), 10,lineHeight*i+10, VAlignment.TOP);
            }
        });
    }
}
