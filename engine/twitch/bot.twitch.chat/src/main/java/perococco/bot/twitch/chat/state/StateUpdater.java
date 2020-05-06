package perococco.bot.twitch.chat.state;

import bot.chat.advanced.event.AdvancedChatEvent;
import bot.chat.advanced.event.ReceivedMessageExtractor;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.message.from.MessageFromTwitch;
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
                                             .map(r -> r.message().accept(mutatorVisitor))
                                             .orElse(IdentityMutator.IDENTITY);
        return identity.mutate(mutator);
    }

}
