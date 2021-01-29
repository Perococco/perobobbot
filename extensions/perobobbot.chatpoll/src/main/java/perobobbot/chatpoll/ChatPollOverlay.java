package perobobbot.chatpoll;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.poll.PollListener;
import perobobbot.poll.PollResult;
import perobobbot.rendering.histogram.Histogram;
import perobobbot.rendering.histogram.HistogramStyle;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
public class ChatPollOverlay implements OverlayClient, PollListener {

    private final int nbValues;

    private final @NonNull HistogramStyle style;

    private Histogram histogram;

    @Override
    public void initialize(@NonNull Overlay overlay) {
        this.histogram = new Histogram(nbValues, overlay, style);
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        if (histogram == null) {
            return;
        }

        iteration.getRenderer().withPrivateContext(r -> {
            histogram.render(r, r.getDrawingSize().toSize().scale(0.25));
        });
    }

    @Override
    public void onPollResult(@NonNull PollResult result, boolean isFinal, @NonNull Duration remainingTime) {
        updateHistogram(result);
    }

    @Override
    public void onPollFailed(@NonNull PollResult lastResult) {
        updateHistogram(lastResult);
    }

    private void updateHistogram(@NonNull PollResult pollResult) {
        Optional.ofNullable(histogram).ifPresent(h -> {
            for (int i = 0; i < h.size(); i++) {
                h.setValue(i, pollResult.numberOfVotesFor(pollResult.getAvailableOptions().get(i)));
            }
        });
    }
}
