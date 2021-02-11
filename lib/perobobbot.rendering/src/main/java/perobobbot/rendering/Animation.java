package perobobbot.rendering;

import lombok.NonNull;

public interface Animation {

    int getNbFrames();

    @NonNull Renderable getFrame(int index);
}
