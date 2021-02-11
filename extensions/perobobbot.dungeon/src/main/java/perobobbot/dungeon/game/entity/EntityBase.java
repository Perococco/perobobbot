package perobobbot.dungeon.game.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.rendering.Renderable;
import perococco.jdgen.api.Position;


@Getter @Setter
public class EntityBase implements Entity {

    private @NonNull Position position = Position.at(0,0);

    private @NonNull Renderable graphics;

    private double t0;
    private double time;

    @Override
    public void initialize(double t0) {
        this.t0 = t0;
        this.time = t0;
    }

    @Override
    public void update(double dt) {
        this.time+=dt;
    }

}
