package bot.chat.core.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Error implements ChatEvent {

    public static Error with(@NonNull Throwable error) {
        return new Error(error);
    }

    @NonNull
    private final Throwable error;

    @Override
    public void accept(@NonNull ChatEventVisitor visitor) {
        visitor.visit(this);
    }

}
