package perobobbot.twitch.chat.message.from;

import lombok.NonNull;

public abstract class MessageFromTwitchAdapter<T> implements MessageFromTwitchVisitor<T> {

    @NonNull
    protected abstract T fallbackVisit(@NonNull MessageFromTwitch messageFromTwitch);

    @NonNull
    @Override
    public T visit(@NonNull CapAck start) {
        return fallbackVisit(start);
    }

    @NonNull
    @Override
    public T visit(@NonNull HostTarget.Stop stop) {
        return fallbackVisit(stop);
    }

    @NonNull
    @Override
    public T visit(@NonNull HostTarget.Start start) {
        return fallbackVisit(start);
    }

    @NonNull
    @Override
    public T visit(@NonNull ClearChat clearChat) {
        return fallbackVisit(clearChat);
    }

    @NonNull
    @Override
    public T visit(@NonNull ClearMsg clearMsg) {
        return fallbackVisit(clearMsg);
    }

    @NonNull
    @Override
    public T visit(@NonNull GenericKnownMessageFromTwitch genericKnownMessageFromTwitch) {
        return fallbackVisit(genericKnownMessageFromTwitch);
    }

    @NonNull
    @Override
    public T visit(@NonNull GlobalUserState globalUserState) {
        return fallbackVisit(globalUserState);
    }

    @NonNull
    @Override
    public T visit(@NonNull InvalidIRCCommand invalidIRCCommand) {
        return fallbackVisit(invalidIRCCommand);
    }

    @NonNull
    @Override
    public T visit(@NonNull Join join) {
        return fallbackVisit(join);
    }

    @NonNull
    @Override
    public T visit(@NonNull Mode mode) {
        return fallbackVisit(mode);
    }

    @NonNull
    @Override
    public T visit(@NonNull Notice notice) {
        return fallbackVisit(notice);
    }

    @NonNull
    @Override
    public T visit(@NonNull Part part) {
        return fallbackVisit(part);
    }

    @NonNull
    @Override
    public T visit(@NonNull PingFromTwitch pingFromTwitch) {
        return fallbackVisit(pingFromTwitch);
    }

    @NonNull
    @Override
    public T visit(@NonNull PongFromTwitch pongFromTwitch) {
        return fallbackVisit(pongFromTwitch);
    }

    @NonNull
    @Override
    public T visit(@NonNull PrivMsgFromTwitch privMsgFromTwitch) {
        return fallbackVisit(privMsgFromTwitch);
    }

    @NonNull
    @Override
    public T visit(@NonNull RoomState roomState) {
        return fallbackVisit(roomState);
    }

    @NonNull
    @Override
    public T visit(@NonNull UnknownMessageFromTwitch unknownMessageFromTwitch) {
        return fallbackVisit(unknownMessageFromTwitch);
    }

    @NonNull
    @Override
    public T visit(@NonNull UserNotice userNotice) {
        return fallbackVisit(userNotice);
    }

    @NonNull
    @Override
    public T visit(@NonNull UserState userState) {
        return fallbackVisit(userState);
    }

    @NonNull
    @Override
    public T visit(@NonNull Welcome welcome) {
        return fallbackVisit(welcome);
    }
}
