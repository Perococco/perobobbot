package perobobbot.poll;

import lombok.NonNull;

import java.time.Duration;

public interface PollListener {

    void onPollResult(@NonNull PollResult result, boolean isFinal, @NonNull Duration remainingTime);

    void onPollFailed(@NonNull PollResult lastResult);
}
