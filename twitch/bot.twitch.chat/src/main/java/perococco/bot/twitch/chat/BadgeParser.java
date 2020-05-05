package perococco.bot.twitch.chat;

import bot.common.lang.MapTool;
import bot.twitch.chat.Badge;
import bot.twitch.chat.Badges;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BadgeParser {

    @NonNull
    public static Badges parse(@NonNull String badgeListFromTag) {
        return new BadgeParser(badgeListFromTag).parse();
    }


    @NonNull
    private final String badgeListFromTag;

    @NonNull
    private Badges parse() {
        final ImmutableMap<String, Badge> badgesByName = Arrays.stream(badgeListFromTag.split(","))
                                                               .map(this::parseSingleBadge)
                                                               .collect(MapTool.collector(Badge::name));
        return new MapBasedBadges(badgesByName);
    }

    @NonNull
    private Badge parseSingleBadge(String singleBadgeFromTag) {
        final String[] tokens = singleBadgeFromTag.split("/");
        return new Badge(tokens[0], Integer.parseInt(tokens[1]));
    }

}
