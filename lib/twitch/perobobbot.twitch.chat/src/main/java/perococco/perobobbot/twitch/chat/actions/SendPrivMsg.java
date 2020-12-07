package perococco.perobobbot.twitch.chat.actions;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.fp.Function1;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.message.to.PrivMsg;
import perococco.perobobbot.twitch.chat.TwitchIO;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class SendPrivMsg implements TwitchIOAction<TwitchIO.DispatchSlip> {

    @NonNull
    private final PrivMsg privMsg;


    public SendPrivMsg(@NonNull Channel channel, @NonNull Function1<? super DispatchContext, ? extends String> message) {
        this(new PrivMsg(channel,message));
    }


    @Override
    public @NonNull CompletionStage<TwitchIO.DispatchSlip> evaluate(@NonNull TwitchIO io) {
        return io.sendToChat(privMsg);
    }
}
