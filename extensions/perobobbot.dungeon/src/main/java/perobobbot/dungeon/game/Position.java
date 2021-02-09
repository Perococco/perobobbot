package perobobbot.dungeon.game;

import lombok.NonNull;
import lombok.Value;
import perococco.jdgen.api.IntPoint;

@Value
public class Position implements IntPoint {

    public static @NonNull Position at(int x, int y) {
        return new Position(x,y);
    }

    int x;
    int y;

    public @NonNull Position moveNorth(int amount) {
        return at(x,y+amount);
    }

    public @NonNull Position moveNorthEast(int amount) {
        return at(x+amount,y-amount);
    }
    public @NonNull Position moveNorthWest(int amount) {
        return at(x-amount,y-amount);
    }

    public @NonNull Position moveSouth(int amount) {
        return at(x,y+amount);
    }
    public @NonNull Position moveSouthEast(int amount) {
        return at(x+amount,y+amount);
    }
    public @NonNull Position moveSouthWest(int amount) {
        return at(x-amount,y+amount);
    }
    public @NonNull Position moveWest(int amount) {
        return at(x-amount,y);
    }
    public @NonNull Position moveEast(int amount) {
        return at(x+amount,y);
    }
}
