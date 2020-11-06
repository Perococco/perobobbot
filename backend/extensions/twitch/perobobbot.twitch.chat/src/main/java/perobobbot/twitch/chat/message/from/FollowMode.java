package perobobbot.twitch.chat.message.from;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FollowMode {

    @NonNull
    public static FollowMode create(int ircValue) {
        return new FollowMode(ircValue);
    }

    public boolean isOff() {
        return followDelay < 0;
    }

    public boolean isOn() {
        return followDelay >= 0;
    }

    @Getter
    private final int followDelay;

    @Override
    public String toString() {
        if (isOff()) {
            return "FollowMode{OFF}";
        }
        return "FollowMode{ON: "+followDelay+" mn}";
    }
}
