package perococco.perobobbot.twitch.chat.actions;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.fp.Function1;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.message.to.PrivMsg;
import perococco.perobobbot.twitch.chat.IO;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class SendPrivMsg implements IOAction<IO.DispatchSlip> {

    @NonNull
    private final PrivMsg privMsg;


    public SendPrivMsg(@NonNull Channel channel, @NonNull Function1<? super DispatchContext, ? extends String> message) {
        this(new PrivMsg(channel,message));
    }


    @Override
    public @NonNull CompletionStage<IO.DispatchSlip> evaluate(@NonNull IO io) {
        return io.sendToChat(privMsg);
    }
}
