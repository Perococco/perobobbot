package perococco.bot.twitch.chat.actions;

import bot.twitch.chat.Channel;
import bot.twitch.chat.message.to.PrivMsg;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.twitch.chat.IO;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class SendPrivMsg implements IOAction<IO.DispatchSlip> {

    @NonNull
    private final PrivMsg privMsg;


    public SendPrivMsg(@NonNull Channel channel, @NonNull String message) {
        this(new PrivMsg(channel,message));
    }


    @Override
    public @NonNull CompletionStage<IO.DispatchSlip> evaluate(@NonNull IO io) {
        return io.sendToChat(privMsg);
    }
}
