package bot.chat.advanced.event;

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

    @Override
    public void accept(@NonNull AdvancedChatEventVisitor<M> visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Error{" +
                "error=" + error.getMessage() +
                '}';
    }
}
