package perococco.perobobbot.twitch.chat.state;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.advanced.event.AdvancedChatEvent;
import perobobbot.chat.advanced.event.ReceivedMessage;
import perobobbot.lang.Identity;
import perobobbot.lang.Mutation;
import perobobbot.twitch.chat.TwitchChatState;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;

@RequiredArgsConstructor
public class StateUpdater {

    @NonNull
    private final Identity<ConnectionState> identity;

    private final MutatorVisitor mutatorVisitor = new MutatorVisitor();

    public @NonNull TwitchChatState updateWith(@NonNull AdvancedChatEvent<MessageFromTwitch> event) {
        final var mutator = event.castToReceivedMessage()
                                             .map(ReceivedMessage::getMessage)
                                             .map(m -> m.accept(mutatorVisitor))
                                             .orElse(Mutation.identity());
        return identity.mutate(mutator);
    }

}
