package perobobbot.twitch.eventsub.manager.spring;

import lombok.NonNull;
import perobobbot.lang.Todo;
import perobobbot.lang.chain.Chain;
import perobobbot.lang.chain.Link;
import perobobbot.twitch.eventsub.api.EventSubRequest;
import perobobbot.twitch.eventsub.api.TwitchRequestContent;

public class TwitchRequestContentDispatcher implements Link<TwitchRequestContent<EventSubRequest>> {

    @Override
    public void call(@NonNull TwitchRequestContent<EventSubRequest> parameter, @NonNull Chain<TwitchRequestContent<EventSubRequest>> chain) {
        //TODO
        Todo.TODO();
    }
}
