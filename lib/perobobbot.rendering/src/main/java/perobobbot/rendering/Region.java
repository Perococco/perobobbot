package perobobbot.rendering;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Region {

    double x;
    double y;
    @NonNull Size size;

    public Region(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.size = Size.with(width,height);
    }

    public double getWidth() {
        return size.getWidth();
    }

    public double getHeight() {
        return size.getHeight();
    }

    public @NonNull Region translateX(double dx) {
        return new Region(x+dx,y,size);
    }

    public @NonNull Region translateY(double dy) {
        return new Region(x,y+dy,size);
    }

    public @NonNull Region growUp(double height) {
        return new Region(x,y-height,new Size(size.getWidth(),size.getHeight()+height));
    }

    public @NonNull Region growDown(double height) {
        return new Region(x,y,new Size(size.getWidth(),size.getHeight()+height));
    }
}
