package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.Capability;
import bot.twitch.chat.message.IRCCommand;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
@Getter
public class CapAck extends MessageFromTwitchBase implements KnownMessageFromTwitch {

    @NonNull
    private final IRCParsing ircParsing;

    @NonNull
    private final ImmutableSet<Capability> capabilities;



    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.CAP;
    }


    @NonNull
    public static CapAck build(@NonNull AnswerBuilderHelper helper) {
        return new CapAck(helper.ircParsing(), helper.capabilities());
    }


}
