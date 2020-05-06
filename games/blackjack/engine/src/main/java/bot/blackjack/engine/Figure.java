package bot.blackjack.engine;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Figure {
    KING("K",13),
    QUEEN("Q",12),
    JACK("J",11),
    TEN("10",10),
    NINE("9",9),
    EIGHT("8",8),
    SEVEN("7",7),
    SIX("6",6),
    FIVE("5",5),
    FOUR("4",4),
    THREE("3",3),
    TWO("2",2),
    ACE("A",1),
    ;

    @NonNull
    @Getter
    private final String symbol;

    @Getter
    private final int rank;

    public boolean isAnAce() {
        return this == Figure.ACE;
    }

    public boolean isNotAnAce() {
        return this != Figure.ACE;
    }


}
