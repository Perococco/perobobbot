package perobbot.twitch.restclient;

public interface AppTwitchService {

    void createEventSubSubscription();

    void deleteEventSubSubscription();

    void getEventSubSubscriptions();

}
