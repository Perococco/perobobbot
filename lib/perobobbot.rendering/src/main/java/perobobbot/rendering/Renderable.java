package perobobbot.rendering;

import lombok.NonNull;

public interface Renderable extends SimpleRenderable {

    Renderable NOP = (r,x,y,s) -> {};



    void render(@NonNull Renderer renderer, double x, double y, double scale);

    @Override
    default void render(@NonNull Renderer renderer) {
        render(renderer,0,0);
    }

    default void render(@NonNull Renderer renderer, double x, double y) {
        render(renderer,x,y,1);
    }


}
