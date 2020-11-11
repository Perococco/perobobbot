package perobobbot.overlay.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OverlaySize {
    _1920_1080(1920,1080),
    _1600_900(1600,900),
    _640_480(640,480),
    ;

    @Getter
    private final int width;
    @Getter
    private final int height;

    public int numberOfPixels() {
        return width*height;
    }
}
