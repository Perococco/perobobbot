package perobobbot.security.com.deser;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.NonNull;
import perobobbot.lang.IdentifiedEnum;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.lang.JsonModuleProvider;
import perobobbot.security.com.IdentificationMode;

import java.util.List;
import java.util.stream.Stream;

public class SecurityJsonModule extends SimpleModule {

    public static @NonNull JsonModuleProvider provider() {
        return () -> List.of(new SecurityJsonModule());
    }

    public SecurityJsonModule() {
        this.setNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        Stream.of(IdentificationMode.class)
              .forEach(this::addIdentifiedEnumToModule);
    }

    private <T extends IdentifiedEnum> void addIdentifiedEnumToModule(Class<T> type) {
        IdentifiedEnumTools.addToModule(this,type);
    }



}
