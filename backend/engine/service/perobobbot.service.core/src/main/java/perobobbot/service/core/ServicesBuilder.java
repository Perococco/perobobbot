package perobobbot.service.core;

import lombok.NonNull;

public interface ServicesBuilder {

    int DEFAULT_PRIORITY = -100;

    @NonNull
    ServicesBuilder addService(@NonNull Object service);

    @NonNull
    Services build();

}
