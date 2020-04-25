package bot.twitch.chat.message.from;

import lombok.NonNull;

public class MessageFromTwitchAdapter implements MessageFromTwitchVisitor {

    protected void fallbackVisit(@NonNull MessageFromTwitch messageFromTwitch) {}

    @Override
    public void visit(@NonNull CapAck start) {
        fallbackVisit(start);
    }

    @Override
    public void visit(@NonNull HostTarget.Stop stop) {
        fallbackVisit(stop);
    }

    @Override
    public void visit(@NonNull HostTarget.Start start) {
        fallbackVisit(start);
    }

    @Override
    public void visit(@NonNull ClearChat clearChat) {
        fallbackVisit(clearChat);
    }

    @Override
    public void visit(@NonNull ClearMsg clearMsg) {
        fallbackVisit(clearMsg);
    }

    @Override
    public void visit(@NonNull GenericKnownMessageFromTwitch genericKnownMessageFromTwitch) {
        fallbackVisit(genericKnownMessageFromTwitch);
    }

    @Override
    public void visit(@NonNull GlobalUserState globalUserState) {
        fallbackVisit(globalUserState);
    }

    @Override
    public void visit(@NonNull InvalidIRCCommand invalidIRCCommand) {
        fallbackVisit(invalidIRCCommand);
    }

    @Override
    public void visit(@NonNull Join join) {
        fallbackVisit(join);
    }

    @Override
    public void visit(@NonNull Mode mode) {
        fallbackVisit(mode);
    }

    @Override
    public void visit(@NonNull Notice notice) {
        fallbackVisit(notice);
    }

    @Override
    public void visit(@NonNull Part part) {
        fallbackVisit(part);
    }

    @Override
    public void visit(@NonNull PingFromTwitch pingFromTwitch) {
        fallbackVisit(pingFromTwitch);
    }

    @Override
    public void visit(@NonNull PongFromTwitch pongFromTwitch) {
        fallbackVisit(pongFromTwitch);
    }

    @Override
    public void visit(@NonNull PrivMsgFromTwitch privMsgFromTwitch) {
        fallbackVisit(privMsgFromTwitch);
    }

    @Override
    public void visit(@NonNull RoomState roomState) {
        fallbackVisit(roomState);
    }

    @Override
    public void visit(@NonNull UnknownMessageFromTwitch unknownMessageFromTwitch) {
        fallbackVisit(unknownMessageFromTwitch);
    }

    @Override
    public void visit(@NonNull UserNotice userNotice) {
        fallbackVisit(userNotice);
    }

    @Override
    public void visit(@NonNull UserState userState) {
        fallbackVisit(userState);
    }

    @Override
    public void visit(@NonNull Welcome welcome) {
        fallbackVisit(welcome);
    }
}
