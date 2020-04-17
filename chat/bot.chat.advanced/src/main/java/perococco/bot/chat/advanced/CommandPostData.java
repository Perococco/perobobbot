package perococco.bot.chat.advanced;

import bot.chat.advanced.Command;
import bot.chat.advanced.DispatchSlip;
import bot.common.lang.Nil;
import lombok.NonNull;

import java.time.Instant;
import java.util.Optional;

/**
 * @author perococco
 **/
public class CommandPostData<M> extends AbstractPostData<DispatchSlip,Command, M> {

    public CommandPostData(@NonNull Command message) {
        super(message);
    }

    @Override
    public @NonNull Optional<RequestPostData<?,M>> asRequestPostData() {
        return Optional.empty();
    }

    @Override
    public void onMessagePosted(@NonNull Instant dispatchingTime) {
        completeWith(new BasicDispatchSlip(dispatchingTime,message()));
    }

}
