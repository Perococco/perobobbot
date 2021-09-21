package perobobbot.lang;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

public interface ObjectMapperFactory {

    int VERSION = 1;

    @NonNull ObjectMapper create();

    @NonNull ObjectMapper createWithExtraModules(@NonNull Module... modules);
}
