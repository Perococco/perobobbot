package perobobbot.dungeon.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ExtraFlag {
    WW(0b00),
    _W(0b10),
    W_(0b01),
    __(0b11),
    ;
    @Getter
    private final int flag;

    public static @NonNull ExtraFlag getByValue(int value) {
        return switch (value) {
            case 0 -> WW;
            case 1 -> W_;
            case 2 -> _W;
            case 3 -> __;
            default -> throw new IllegalArgumentException("Value for extraFlag must be between 0 and 3 inclusive : value="+value);
        };
    }
}
