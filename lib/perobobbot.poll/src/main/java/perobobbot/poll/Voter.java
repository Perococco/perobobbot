package perobobbot.poll;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.ChatUser;
import perobobbot.lang.Platform;

@Value
public class Voter {

    public static @NonNull Voter from(@NonNull ChatUser chatUser) {
        return new Voter(chatUser.getUserId(),chatUser.getPlatform());
    }

    @NonNull String id;

    @NonNull Platform platform;

    public @NonNull Vote createVote(@NonNull String choice) {
        return new Vote(this,choice);
    }

}
