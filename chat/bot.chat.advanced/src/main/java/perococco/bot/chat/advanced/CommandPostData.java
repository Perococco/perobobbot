package perococco.bot.chat.advanced;

import bot.chat.advanced.Command;
import bot.common.lang.Nil;
import lombok.NonNull;

import java.util.Optional;

/**
 * @author perococco
 **/
public class CommandPostData extends AbstractPostData<Nil> {

    public CommandPostData(@NonNull Command message) {
        super(message);
    }

    @Override
    public @NonNull Optional<RequestPostData<?>> asRequestPostData() {
        return Optional.empty();
    }

    @Override
    public void onMessagePosted() {
        completeWith(Nil.NIL);
    }
}
