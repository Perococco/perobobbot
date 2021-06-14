package perobobbot.twitch.eventsub.api.event;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

@Value
public class Outcome {
    @NonNull String id;
    @NonNull String title;
    @NonNull OutcomeColor color;
    int users;
    int channelPoints;
    @NonNull ImmutableList<TopPredictor> topPredictors;


    @java.beans.ConstructorProperties({"id", "title", "color", "users", "channelPoints", "topPredictors"})
    public Outcome(@NonNull String id, @NonNull String title, @NonNull OutcomeColor color, int users, int channelPoints, ImmutableList<TopPredictor> topPredictors) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.users = users;
        this.channelPoints = channelPoints;
        this.topPredictors = topPredictors == null ? ImmutableList.of():topPredictors;
    }
}
