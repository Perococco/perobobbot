package perobobbot.frontfx.model.dialog;

import javafx.stage.Stage;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

@Value
public class DialogData<O> {

    @NonNull Class<O> resultType;

    @NonNull Stage stage;

    @NonNull CompletionStage<Optional<O>> resultStage;

    @SuppressWarnings("unchecked")
    @NonNull
    public <T> Optional<DialogData<T>> cast(@NonNull Class<T> type) {
        if (resultType.equals(type)) {
            return Optional.of((DialogData<T>) this);
        }
        return Optional.empty();
    }
}
