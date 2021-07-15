package perobobbot.lang;

import lombok.NonNull;

public interface IdentifiedEnumWithAlternateIdentification extends IdentifiedEnum {

    @NonNull
    String getAlternateIdentification();
    
}
