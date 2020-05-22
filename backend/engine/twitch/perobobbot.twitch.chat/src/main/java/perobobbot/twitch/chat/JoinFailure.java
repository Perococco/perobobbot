package perobobbot.twitch.chat;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.message.from.NoticeId;

@RequiredArgsConstructor
public class JoinFailure extends TwitchChatException{

    @NonNull
    private final NoticeId msgId;

    @NonNull
    private final String message;


}
