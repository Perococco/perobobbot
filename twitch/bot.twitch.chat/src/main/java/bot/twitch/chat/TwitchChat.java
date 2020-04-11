package bot.twitch.chat;

import bot.common.lang.Nil;
import bot.twitch.chat.message.TwitchCommand;
import bot.twitch.chat.message.TwitchRequest;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface TwitchChat {

    @NonNull
    CompletionStage<Nil> sendCommand(@NonNull TwitchCommand twitchCommand);

    @NonNull
    <A> CompletionStage<A> sendRequest(@NonNull TwitchRequest<A> twitchRequest);

    void addTwitchChatListener(@NonNull TwitchChatListener listener);

}
