package bot.twitch.chat;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

/**
 * @author perococco
 **/
public class TwitchMarkers {

    public static final Marker TWITCH = MarkerManager.getMarker("TWITCH");
    public static final Marker TWITCH_CHAT = MarkerManager.getMarker("TWITCH_CHAT").addParents(TWITCH);
}
