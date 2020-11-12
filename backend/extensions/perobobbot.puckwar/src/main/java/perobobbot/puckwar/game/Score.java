package perobobbot.puckwar.game;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Lazy;
import perobobbot.lang.User;

import java.time.Instant;
import java.util.Comparator;

@RequiredArgsConstructor
public class Score {

    private static final Comparator<Score> TIE_RESOLVER = Comparator.comparing(Score::getTimeStamp)
                                                                    .thenComparing(Score::getUser, User.COMPARE_BY_ID);

    public static final Comparator<Score> LOWER_IS_FIRST = Comparator.comparingDouble(Score::getScore)
                                                                      .thenComparing(TIE_RESOLVER);

    public static final Comparator<Score> HIGHER_IS_FIRST = Comparator.comparingDouble(Score::getScore)
                                                                       .reversed()
                                                                       .thenComparing(TIE_RESOLVER);

    @Getter
    private final @NonNull User user;

    @Getter
    private final @NonNull Instant timeStamp;

    @Getter
    private final @NonNull double score;

    private final Lazy<String> lazyText = Lazy.basic(this::createScoreText);

    private @NonNull String createScoreText() {
        return String.format("%-30s : %8.2f",user.getUserName(),score);
    }

    public @NonNull String getText() {
        return lazyText.get();
    }

}
