package perobobbot.lang;

import lombok.NonNull;

public interface IdentifiedEnum extends Identified<String> {

    @NonNull
    String getIdentification();

}
