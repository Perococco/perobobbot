package perobobbot.lang;

import lombok.NonNull;

import java.util.Optional;

public interface Handler<I, O> {

    @NonNull Optional<O> handle(@NonNull I input);

    default <P> Handler<I, P> then(@NonNull Handler<? super O, ? extends P> after) {
        return i -> this.handle(i).flatMap(after::handle);
    }

}
