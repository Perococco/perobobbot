package perobobbot.lang;

import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface AsyncCallback<O> {

    @NonNull
    CompletionStage<O> call();
}
