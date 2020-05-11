package perococco.perobobbot.twitch.chat.state;

import perobobbot.chat.advanced.event.AdvancedChatEvent;
import perobobbot.chat.advanced.event.ReceivedMessageExtractor;
import perobobbot.twitch.chat.TwitchChatState;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StateUpdater {

    @NonNull
    private final ConnectionIdentity identity;

    private final ReceivedMessageExtractor<MessageFromTwitch> extractor = new ReceivedMessageExtractor<>();

    private final MutatorVisitor mutatorVisitor = new MutatorVisitor();

    public @NonNull TwitchChatState updateWith(@NonNull AdvancedChatEvent<MessageFromTwitch> event) {
        final IdentityMutator mutator = event.accept(extractor)
                                             .map(r -> r.getMessage().accept(mutatorVisitor))
                                             .orElse(IdentityMutator.IDENTITY);
        return identity.mutate(mutator);
    }

}
