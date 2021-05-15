package perobobbot.twitch.client.api;

public interface TwitchService {

    @TokenRequired(type = TokenType.USER_TOKEN)
    void getStreamTags();

    @TokenRequired(type = TokenType.CLIENT_TOKEN)
    void createEventSubSubscription();

    @TokenRequired(type = TokenType.CLIENT_TOKEN)
    void deleteEventSubSubscription();

    @TokenRequired(type = TokenType.CLIENT_TOKEN)
    void getEventSubSubscriptions();

}
