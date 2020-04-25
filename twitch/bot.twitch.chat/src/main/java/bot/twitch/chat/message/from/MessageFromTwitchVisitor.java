package bot.twitch.chat.message.from;

import bot.common.lang.fp.Consumer1;
import lombok.NonNull;

public interface MessageFromTwitchVisitor {

    @NonNull
    default Consumer1<MessageFromTwitch> toConsumer() {
        return m -> m.accept(this);
    }

    void visit(@NonNull CapAck start);

    void visit(@NonNull HostTarget.Stop stop);

    void visit(@NonNull HostTarget.Start start);

    void visit(@NonNull ClearChat clearChat);

    void visit(@NonNull ClearMsg clearMsg);

    void visit(@NonNull GenericKnownMessageFromTwitch genericKnownMessageFromTwitch);

    void visit(@NonNull GlobalUserState globalUserState);

    void visit(@NonNull InvalidIRCCommand invalidIRCCommand);

    void visit(@NonNull Join join);

    void visit(@NonNull Mode mode);

    void visit(@NonNull Notice notice);

    void visit(@NonNull Part part);

    void visit(@NonNull PingFromTwitch pingFromTwitch);

    void visit(@NonNull PongFromTwitch pongFromTwitch);

    void visit(@NonNull PrivMsgFromTwitch privMsgFromTwitch);

    void visit(@NonNull RoomState roomState);

    void visit(@NonNull UnknownMessageFromTwitch unknownMessageFromTwitch);

    void visit(@NonNull UserNotice userNotice);

    void visit(@NonNull UserState userState);

    void visit(@NonNull Welcome welcome);
}
