package perococco.perobobbot.twitch.chat.state;

import lombok.NonNull;
import perobobbot.twitch.chat.message.from.*;
import perococco.perobobbot.twitch.chat.state.mutator.ChanelAdder;
import perococco.perobobbot.twitch.chat.state.mutator.ChanelRemover;
import perococco.perobobbot.twitch.chat.state.mutator.UsernameSetter;

public class MutatorVisitor extends MessageFromTwitchAdapter<IdentityMutator> {

    @NonNull
    @Override
    protected IdentityMutator fallbackVisit(@NonNull MessageFromTwitch messageFromTwitch) {
        return IdentityMutator.IDENTITY;
    }

    @NonNull
    @Override
    public IdentityMutator visit(@NonNull UserState userState) {
        return new ChanelAdder(userState);
    }

    @NonNull
    @Override
    public IdentityMutator visit(@NonNull Part part) {
        return new ChanelRemover(part.getChannel());
    }

    @NonNull
    @Override
    public IdentityMutator visit(@NonNull Welcome welcome) {
        return new UsernameSetter(welcome.getUserName());
    }
}
