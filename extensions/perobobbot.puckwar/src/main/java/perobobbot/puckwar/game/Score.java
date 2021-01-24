package perobobbot.puckwar.game;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.ChatUser;
import perobobbot.lang.EllipsedString;
import perobobbot.lang.Lazy;

import java.time.Instant;
import java.util.Comparator;

@RequiredArgsConstructor
public class Score {

    private static final Comparator<Score> TIE_RESOLVER = Comparator.comparing(Score::getTimeStamp)
                                                                    .thenComparing(Score::getUser, ChatUser.COMPARE_BY_ID);

    public static final Comparator<Score> LOWER_IS_FIRST = Comparator.comparingDouble(Score::getScore)
                                                                      .thenComparing(TIE_RESOLVER);

    public static final Comparator<Score> HIGHER_IS_FIRST = Comparator.comparingDouble(Score::getScore)
                                                                       .reversed()
                                                                       .thenComparing(TIE_RESOLVER);

    @Getter
    private final @NonNull ChatUser user;

    @Getter
    private final @NonNull Instant timeStamp;

    @Getter
    private final double score;

    private final Lazy<String> lazyText = Lazy.basic(this::createScoreText);

    private @NonNull String createScoreText() {
        return String.format("%-10s : %6.1f", EllipsedString.create(user.getUserName(),10),score);
    }

    public @NonNull String getScoreText() {
        return lazyText.get();
    }

    public @NonNull String getUserName() {
        return user.getUserName();
    }

}
