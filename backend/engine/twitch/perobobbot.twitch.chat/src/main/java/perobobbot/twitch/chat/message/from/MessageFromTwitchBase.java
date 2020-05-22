package perobobbot.twitch.chat.message.from;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.irc.IRCParsing;
import perobobbot.twitch.chat.Badge;
import perobobbot.twitch.chat.Badges;
import perococco.perobobbot.twitch.chat.BadgesParser;

import java.util.Optional;

public abstract class MessageFromTwitchBase implements MessageFromTwitch {

    @NonNull
    private final Badges badges;

    @NonNull
    @Getter
    private final IRCParsing ircParsing;

    public MessageFromTwitchBase(@NonNull IRCParsing ircParsing) {
        this.ircParsing = ircParsing;
        this.badges = ircParsing.tagValue("badges")
                .<Badges>map(BadgesParser::parse)
                .orElse(EMPTY);
    }

    @Override
    public @NonNull Optional<Badge> findBadge(@NonNull String badgeName) {
        return badges.findBadge(badgeName);
    }

}
