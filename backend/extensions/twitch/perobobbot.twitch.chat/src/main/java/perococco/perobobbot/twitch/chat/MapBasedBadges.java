package perococco.perobobbot.twitch.chat;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.Badge;
import perobobbot.twitch.chat.Badges;

import java.util.Optional;

@RequiredArgsConstructor
public class MapBasedBadges implements Badges {

    @NonNull
    @Getter
    private final ImmutableMap<String, Badge> badgesByName;

    @Override
    public boolean hasBadge(@NonNull String badgeName) {
        return badgesByName.containsKey(badgeName);
    }

    @Override
    public @NonNull Optional<Badge> findBadge(@NonNull String badgeName) {
        return Optional.ofNullable(badgesByName.get(badgeName));
    }
}
