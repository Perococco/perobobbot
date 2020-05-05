package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.Badge;
import bot.twitch.chat.Badges;
import lombok.Getter;
import lombok.NonNull;
import perococco.bot.twitch.chat.BadgeParser;

import java.util.Optional;

public abstract class MessageFromTwitchBase implements MessageFromTwitch {

    @NonNull
    private final Badges badges;

    @NonNull
    @Getter
    private final IRCParsing ircParsing;

    public MessageFromTwitchBase(@NonNull IRCParsing ircParsing) {
        this.ircParsing = ircParsing;
        this.badges = ircParsing.tagValue("badges").map(BadgeParser::parse).orElse(EMPTY);
    }

    @Override
    public @NonNull Optional<Badge> findBadge(@NonNull String badgeName) {
        return badges.findBadge(badgeName);
    }

}
