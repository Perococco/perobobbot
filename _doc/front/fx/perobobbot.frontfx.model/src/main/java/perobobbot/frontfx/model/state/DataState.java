package perobobbot.frontfx.model.state;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Bot;
import perobobbot.security.com.SimpleUser;

@Value
@Builder(toBuilder = true)
public class DataState {

    public static @NonNull DataState empty() {
        return new DataState(ImmutableList.of(), ImmutableList.of());
    }

    @NonNull ImmutableList<SimpleUser> users;
    @NonNull ImmutableList<Bot> bots;

    public @NonNull DataState withUsers(@NonNull ImmutableList<SimpleUser> users) {
        if (this.users == users) {
            return this;
        }
        return toBuilder().users(users).build();
    }

    public @NonNull DataState withBots(@NonNull ImmutableList<Bot> bots) {
        if (this.bots == bots) {
            return this;
        }
        return toBuilder().bots(bots).build();
    }
}
