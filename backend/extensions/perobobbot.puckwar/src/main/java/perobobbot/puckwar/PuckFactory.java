package perobobbot.puckwar;

import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.util.Random;

@RequiredArgsConstructor
public class PuckFactory {

    public static final Color[] COLORS = {
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.magenta,
            Color.ORANGE,
            Color.yellow
    };

    private final Random random = new Random();

    private final int width;

    private final int height;

    private final int puckSize;

    public Puck randomPuck(int index) {
        var x = random.nextInt(width);
        var y= random.nextInt(height);
        var vx = random.nextInt(width/10);
        var vy= random.nextInt(height/10);
        var color = COLORS[random.nextInt(COLORS.length)];
        return new Puck(x,y,vx,vy,puckSize,color);
    }
}
