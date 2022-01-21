package perobobbot.twitch.eventsub.manager._private;

import perobobbot.lang.MessageSaver;

public class TwitchRequestSaver extends MessageSaver {

    public TwitchRequestSaver() {
        super("twitch_notification_",".json");
    }

}
