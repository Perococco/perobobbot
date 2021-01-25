package perobobbot.connect4;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.grid.Connect4Geometry;
import perobobbot.connect4.grid.Connect4GridDrawer;

import java.awt.image.BufferedImage;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Connect4Grid {

    @Getter
    private final @NonNull BufferedImage image;

    public Connect4Grid(int positionRadius) {
        final var geometry = new Connect4Geometry(positionRadius,positionRadius/4,positionRadius/2);
        this.image = new Connect4GridDrawer(geometry).draw();
    }

}
