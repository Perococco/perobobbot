package perobobbot.lang;

import lombok.NonNull;

import java.util.Optional;

public interface Owned {

    @NonNull Optional<IdentityInfo> getOwner();

}
