package perobobbot.ndi;

import lombok.NonNull;

import java.awt.*;

public interface OverlayDrawer {

    void draw(@NonNull Graphics2D graphics2D, int width, int height);
}
