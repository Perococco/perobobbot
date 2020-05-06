package perococco.bot.twitch.chat;

import bot.chat.advanced.Request;
import bot.chat.advanced.RequestAnswerMatcher;
import bot.common.lang.ThrowableTool;
import bot.common.lang.fp.TryResult;
import bot.twitch.chat.TwitchMarkers;
import bot.twitch.chat.UnknownIRCCommand;
import bot.twitch.chat.message.IRCCommand;
import bot.twitch.chat.message.from.InvalidIRCCommand;
import bot.twitch.chat.message.from.KnownMessageFromTwitch;
import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.to.RequestToTwitch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perococco.bot.twitch.chat.state.ConnectionIdentity;

import java.util.Optional;

/**
 * @author perococco
 **/
@Log4j2
@RequiredArgsConstructor
public class TwitchMatcher implements RequestAnswerMatcher<MessageFromTwitch> {

    @NonNull
    private final ConnectionIdentity connectionIdentity;

    @Override
    public @NonNull <A> Optional<TryResult<Throwable,A>> performMatch(@NonNull Request<A> request, @NonNull MessageFromTwitch answer) {
        if (request instanceof RequestToTwitch) {
            final RequestToTwitch<A> requestToTwitch = (RequestToTwitch<A>) request;
            try {
                if (answer instanceof InvalidIRCCommand) {
                    return handleInvalidAnswerType(requestToTwitch, (InvalidIRCCommand) answer);
                }
                return performMatch(requestToTwitch, answer);
            } catch (Exception e) {
                ThrowableTool.interruptThreadIfCausedByInterruption(e);
                LOG.warn(TwitchMarkers.TWITCH_CHAT, "Error while performing message matching",e);
            }
        }
        return Optional.empty();
    }

    @NonNull
    private <A> Optional<TryResult<Throwable, A>> handleInvalidAnswerType(RequestToTwitch<A> request, InvalidIRCCommand answer) {
        final String unknownCommand = answer.requestedCommand();
        if (unknownCommand.equalsIgnoreCase(request.commandInPayload())) {
            return Optional.of(TryResult.failure(new UnknownIRCCommand(request.commandInPayload())));
        }
        return Optional.empty();
    }

    @Override
    public boolean shouldPerformMatching(@NonNull MessageFromTwitch message) {
        if (message instanceof KnownMessageFromTwitch) {
            final KnownMessageFromTwitch messageFromTwitch = (KnownMessageFromTwitch) message;
            return messageFromTwitch.command() != IRCCommand.PRIVMSG;
        }
        return false;
    }

    @NonNull
    private <A> Optional<TryResult<Throwable,A>> performMatch(@NonNull RequestToTwitch<A> request, @NonNull MessageFromTwitch answer) {
        return connectionIdentity.applyWithTwitchState(s -> request.isMyAnswer(answer, s));
    }


}
