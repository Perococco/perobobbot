package perococco.bot.twitch.chat;

import bot.chat.advanced.Request;
import bot.chat.advanced.RequestAnswerMatcher;
import bot.common.lang.ReadOnlyIdentity;
import bot.common.lang.ThrowableTool;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.TwitchMarkers;
import bot.twitch.chat.message.from.KnownMessageFromTwitch;
import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.to.RequestToTwitch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * @author perococco
 **/
@Log4j2
@RequiredArgsConstructor
public class TwitchMatcher implements RequestAnswerMatcher<MessageFromTwitch> {

    @NonNull
    private final ConnectionIdentity connectionIdentity;

    @Override
    public @NonNull <A> Optional<A> performMatch(@NonNull Request<A> request, @NonNull MessageFromTwitch answer) {
        if (request instanceof RequestToTwitch) {
            try {
                return performMatch((RequestToTwitch<A>) request, answer);
            } catch (Exception e) {
                ThrowableTool.interruptThreadIfCausedByInterruption(e);
                LOG.warn(TwitchMarkers.TWITCH_CHAT, "Error while performing message matching",e);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean shouldPerformMatching(@NonNull MessageFromTwitch message) {
        if (message instanceof KnownMessageFromTwitch) {
            switch (((KnownMessageFromTwitch) message).command()) {
                case JOIN:
                case PART:
                case NOTICE:
                case USERNOTICE:
                case CAP:
                case RPL_WELCOME:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    @NonNull
    private <A> Optional<A> performMatch(@NonNull RequestToTwitch<A> request, @NonNull MessageFromTwitch answer) {
        return connectionIdentity.applyWithTwitchState(s -> request.isAnswer(answer, s));
    }


}
