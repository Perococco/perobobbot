package perobobbot.puckwar;

import lombok.NonNull;
import perobobbot.common.lang.MathTool;

import java.awt.*;

public class Puck {

    private float x;
    private float y;
    private float vx;
    private float vy;
    private int size = 6;
    private Color color;

    public Puck(float x, float y, float vx, float vy, int puckSize, Color color) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.size = puckSize;
        this.color = color;
    }

    public @NonNull Puck update(int width, int height, float dt) {
        x = MathTool.mod(x+dt*vx,width);
        y = MathTool.mod(y+dt*vy,height);
        return this;
    }

    public void draw(@NonNull Graphics2D g2) {
        final float ofx = Math.round(x-size*0.5f);
        final float ofy = Math.round(y-size*0.5f);
        g2.translate(ofx,ofy);
        g2.setPaint(color);
        g2.fillOval(0,0, size, size);
        g2.translate(-ofx,-ofy);
    }
}
