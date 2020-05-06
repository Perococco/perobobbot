package bot.twitch.chat;

import bot.twitch.chat.message.from.NoticeId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JoinFailure extends TwitchChatException{

    @NonNull
    private final NoticeId msgId;

    @NonNull
    private final String message;


}
