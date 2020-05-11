package perobobbot.chat.advanced.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Error<M> implements AdvancedChatEvent<M> {

    @NonNull
    public static <M> Error<M> with(@NonNull Throwable error) {
        return new Error<>(error);
    }

    @NonNull
    private final Throwable error;

    @NonNull
    @Override
    public <T> T accept(@NonNull AdvancedChatEventVisitor<M,T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Error{" +
                "error=" + error.getMessage() +
                '}';
    }
}
