package perococco.perobobbot.twitch.chat.state;

import lombok.NonNull;
import perobobbot.lang.Mutation;
import perobobbot.twitch.chat.message.from.*;
import perococco.perobobbot.twitch.chat.state.mutator.ChanelRemover;

public class MutatorVisitor extends MessageFromTwitchAdapter<Mutation<ConnectionState>> {

    @NonNull
    @Override
    protected Mutation<ConnectionState> fallbackVisit(@NonNull MessageFromTwitch messageFromTwitch) {
        return s -> s;
    }

    @NonNull
    @Override
    public Mutation<ConnectionState> visit(@NonNull Part part) {
        return new ChanelRemover(part.getChannel());
    }

}
