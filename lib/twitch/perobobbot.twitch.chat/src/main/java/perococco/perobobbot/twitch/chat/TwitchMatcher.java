package perococco.perobobbot.twitch.chat;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.advanced.Request;
import perobobbot.chat.advanced.RequestAnswerMatcher;
import perobobbot.lang.Identity;
import perobobbot.lang.ThrowableTool;
import perobobbot.lang.fp.TryResult;
import perobobbot.twitch.chat.TwitchMarkers;
import perobobbot.twitch.chat.UnknownIRCCommand;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.InvalidIRCCommand;
import perobobbot.twitch.chat.message.from.KnownMessageFromTwitch;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.to.RequestToTwitch;
import perococco.perobobbot.twitch.chat.state.ConnectionIdentity;
import perococco.perobobbot.twitch.chat.state.ConnectionState;

import java.util.Optional;

/**
 * @author perococco
 **/
@Log4j2
@RequiredArgsConstructor
public class TwitchMatcher implements RequestAnswerMatcher<MessageFromTwitch> {

    @NonNull
    private final Identity<ConnectionState> connectionIdentity;

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
        final String unknownCommand = answer.getRequestedCommand();
        if (unknownCommand.equalsIgnoreCase(request.commandInPayload())) {
            return Optional.of(TryResult.failure(new UnknownIRCCommand(request.commandInPayload())));
        }
        return Optional.empty();
    }

    @Override
    public boolean shouldPerformMatching(@NonNull MessageFromTwitch message) {
        if (message instanceof KnownMessageFromTwitch) {
            final KnownMessageFromTwitch messageFromTwitch = (KnownMessageFromTwitch) message;
            return messageFromTwitch.getCommand() != IRCCommand.PRIVMSG;
        }
        return false;
    }

    @NonNull
    private <A> Optional<TryResult<Throwable,A>> performMatch(@NonNull RequestToTwitch<A> request, @NonNull MessageFromTwitch answer) {
        return connectionIdentity.get(s -> request.isMyAnswer(answer, s));
    }


}
