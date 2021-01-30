package perobobbot.connect4;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@RequiredArgsConstructor
public enum TokenType {
    YELLOW(new Color(224, 196, 23, 255)),
    RED(new Color(184, 20, 20, 255)),
    ;

    @Getter
    private final @NonNull Color color;

    public @NonNull TokenType getOtherType() {
        return switch (this) {
            case YELLOW -> RED;
            case RED -> YELLOW;
        };

    }
}
