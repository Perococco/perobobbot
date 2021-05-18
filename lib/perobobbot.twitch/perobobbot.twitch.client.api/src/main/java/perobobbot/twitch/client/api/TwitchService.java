package perobobbot.twitch.client.api;

import perobobbot.lang.Nil;
import perobobbot.lang.TokenType;

import java.util.concurrent.CompletionStage;

public interface TwitchService {

    @TokenRequired(type = TokenType.USER_TOKEN)
    CompletionStage<Nil> getStreamTags();

    @TokenRequired(type = TokenType.CLIENT_TOKEN)
    CompletionStage<Nil> createEventSubSubscription();

    @TokenRequired(type = TokenType.CLIENT_TOKEN)
    CompletionStage<Nil> deleteEventSubSubscription();

    @TokenRequired(type = TokenType.CLIENT_TOKEN)
    CompletionStage<Nil> getEventSubSubscriptions();

}
