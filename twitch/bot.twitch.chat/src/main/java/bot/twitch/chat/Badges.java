package bot.twitch.chat;

import lombok.NonNull;

import java.util.Optional;

public interface Badges {

    @NonNull
    Optional<Badge> findBadge(@NonNull String badgeName);


    @NonNull
    Badges EMPTY = badgeName -> Optional.empty();


}
