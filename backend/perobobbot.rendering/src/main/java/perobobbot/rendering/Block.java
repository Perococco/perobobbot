package perobobbot.rendering;

import lombok.NonNull;

public interface Block {

    /**
     * @return the maximal bounds of the block when renderer
     */
    @NonNull Size getSize();

    /**
     * Draw this block
     */
    void draw(double x, double y, @NonNull HAlignment hAlignment, @NonNull VAlignment vAlignment);

    default void draw() {
        draw(0,0,HAlignment.LEFT,VAlignment.TOP);
    }
    default void draw(@NonNull Positioning positioning) {
        draw(positioning.getX(),positioning.getY(),positioning.getHAlignment(),positioning.getVAlignment());
    }


    Block EMPTY = new Block() {
        @Override
        public @NonNull Size getSize() {
            return Size._0_0;
        }

        @Override
        public void draw(double x, double y, @NonNull HAlignment hAlignment, @NonNull VAlignment vAlignment) {}


    };
}
