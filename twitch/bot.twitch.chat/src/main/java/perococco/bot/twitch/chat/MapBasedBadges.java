package perococco.bot.twitch.chat;

import bot.twitch.chat.Badge;
import bot.twitch.chat.Badges;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MapBasedBadges implements Badges {

    @NonNull
    private final ImmutableMap<String, Badge> badgesByName;

    @Override
    public @NonNull Optional<Badge> findBadge(@NonNull String badgeName) {
        return Optional.ofNullable(badgesByName.get(badgeName));
    }
}
