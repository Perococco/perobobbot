package perobobbot.twitch.chat.message.from;

import perobobbot.common.lang.fp.Function1;
import lombok.NonNull;

public interface MessageFromTwitchVisitor<T> {

    @NonNull
    default Function1<MessageFromTwitch, T> toFunction() {
        return m -> m.accept(this);
    }

    @NonNull
    T visit(@NonNull CapAck start);

    @NonNull
    T visit(@NonNull HostTarget.Stop stop);

    @NonNull
    T visit(@NonNull HostTarget.Start start);

    @NonNull
    T visit(@NonNull ClearChat clearChat);

    @NonNull
    T visit(@NonNull ClearMsg clearMsg);

    @NonNull
    T visit(@NonNull GenericKnownMessageFromTwitch genericKnownMessageFromTwitch);

    @NonNull
    T visit(@NonNull GlobalUserState globalUserState);

    @NonNull
    T visit(@NonNull InvalidIRCCommand invalidIRCCommand);

    @NonNull
    T visit(@NonNull Join join);

    @NonNull
    T visit(@NonNull Mode mode);

    @NonNull
    T visit(@NonNull Notice notice);

    @NonNull
    T visit(@NonNull Part part);

    @NonNull
    T visit(@NonNull PingFromTwitch pingFromTwitch);

    @NonNull
    T visit(@NonNull PongFromTwitch pongFromTwitch);

    @NonNull
    T visit(@NonNull PrivMsgFromTwitch privMsgFromTwitch);

    @NonNull
    T visit(@NonNull RoomState roomState);

    @NonNull
    T visit(@NonNull UnknownMessageFromTwitch unknownMessageFromTwitch);

    @NonNull
    T visit(@NonNull UserNotice userNotice);

    @NonNull
    T visit(@NonNull UserState userState);

    @NonNull
    T visit(@NonNull Welcome welcome);
}
