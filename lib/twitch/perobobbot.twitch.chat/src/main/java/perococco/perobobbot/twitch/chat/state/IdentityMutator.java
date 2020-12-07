package perococco.perobobbot.twitch.chat.state;

import lombok.NonNull;

public interface IdentityMutator {

    IdentityMutator IDENTITY = v -> v;

    @NonNull
    ConnectionState mutate(@NonNull ConnectionState currentValue);

    @NonNull
    default IdentityMutator then(@NonNull IdentityMutator identityMutator) {
        return v -> identityMutator.mutate(this.mutate(v));
    }

}
