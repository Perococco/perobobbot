package perococco.bot.twitch.chat;

import bot.chat.advanced.MessageConverter;
import bot.common.irc.IRCParser;
import bot.common.irc.IRCParsing;
import bot.common.lang.ThrowableTool;
import bot.twitch.chat.TwitchMarkers;
import bot.twitch.chat.message.IRCCommand;
import bot.twitch.chat.message.from.AnswerBuilderHelper;
import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.from.UnknownMessageFromTwitch;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

/**
 * @author perococco
 **/
@Log4j2
public class TwitchMessageConverter implements MessageConverter<MessageFromTwitch> {

    @NonNull
    private final IRCParser ircParser = IRCParser.create();

    @Override
    public @NonNull Optional<MessageFromTwitch> convert(@NonNull String messageAsString) {
        try {
            final IRCParsing ircParsing = ircParser.parse(messageAsString);
            final Optional<IRCCommand> command = IRCCommand.findFromString(ircParsing.getCommand());

            final MessageFromTwitch messageFromTwitch =  command.map(cmd -> buildKnownAnswer(cmd,ircParsing))
                                                      .orElseGet(() -> buildUnknownAnswer(ircParsing));

            return Optional.of(messageFromTwitch);
        } catch (Exception e) {
            ThrowableTool.interruptThreadIfCausedByInterruption(e);
            LOG.warn(TwitchMarkers.TWITCH_CHAT,"Fail to convert message '"+messageAsString+"'",e);
            return Optional.empty();
        }
    }

    @NonNull
    private MessageFromTwitch buildKnownAnswer(@NonNull IRCCommand command, @NonNull IRCParsing ircParsing) {
        final AnswerBuilderHelper helper = new AnswerBuilderHelper(ircParsing);
        return command.buildTwitchAnswer(helper);
    }

    @NonNull
    private MessageFromTwitch buildUnknownAnswer(@NonNull IRCParsing ircParsing) {
        LOG.warn(TwitchMarkers.TWITCH_CHAT,"Unknown command : '{}' for message '{}'", ircParsing.getCommand(),ircParsing.getRawMessage());
        return new UnknownMessageFromTwitch(ircParsing);
    }

}
