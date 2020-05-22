package perococco.perobobbot.blackjack.engine;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perbobbot.blackjack.engine.Figure;
import perbobbot.blackjack.engine.Suit;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCardSymbol {

    @NonNull
    public static String get(@NonNull Figure figure, @NonNull Suit suit) {
        return new GetCardSymbol(figure, suit).getSymbol();
    }

    private static final byte[] BASE = {(byte) 0xfe, (byte) 0xff, (byte) 0xd8,0x3c, (byte) 0xdc, 0x00};

    @NonNull
    private final Figure figure;

    @NonNull
    private final Suit suit;

    @NonNull
    private String getSymbol() {
        final int figurePart = figure.getRank()+(figure.getRank()>11?1:0);
        final int suitPart = getSuitPart();

        final byte[] bytes = BASE.clone();
        bytes[5] = (byte)(figurePart|suitPart);
        return new String(bytes, StandardCharsets.UTF_16);
    }

    private int getSuitPart() {
        return switch (suit) {
            case SPADES -> 0xA0;
            case HEARTS ->  0xB0;
            case DIAMONDS ->  0xC0;
            case CLUBS ->  0xD0;
        };
    }
}
