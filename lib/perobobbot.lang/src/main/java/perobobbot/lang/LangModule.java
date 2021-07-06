package perobobbot.lang;

import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.NonNull;

import java.util.List;

public class LangModule extends SimpleModule {

    public static @NonNull JsonModuleProvider provider() {
        return () -> List.of(new LangModule());
    }

    public LangModule() {
        IdentifiedEnumTools.addToModule(this,Platform.class);
    }
}
