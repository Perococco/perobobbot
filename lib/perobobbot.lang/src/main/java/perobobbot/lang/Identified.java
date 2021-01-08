package perobobbot.lang;

import lombok.NonNull;

public interface Identified<I> {

    @NonNull
    I getIdentification();

}
