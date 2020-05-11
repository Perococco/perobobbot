package perococco.perobobbot.twitch.chat.state;

import lombok.NonNull;

public interface IdentityMutator {

    IdentityMutator IDENTITY = v -> v;

    @NonNull
    ConnectionValue mutate(@NonNull ConnectionValue currentValue);

    @NonNull
    default IdentityMutator then(@NonNull IdentityMutator identityMutator) {
        return v -> identityMutator.mutate(this.mutate(v));
    }

}
