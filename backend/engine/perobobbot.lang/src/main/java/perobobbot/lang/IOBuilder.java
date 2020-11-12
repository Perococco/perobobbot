package perobobbot.lang;

import lombok.NonNull;

public interface IOBuilder {

    IO build();

    @NonNull
    IOBuilder add(@NonNull Platform platform,@NonNull PlatformIO platformIO);
}
