package perobobbot.lang;

import com.fasterxml.jackson.databind.Module;
import lombok.NonNull;

import java.util.List;

public interface JsonModuleProvider {

    @NonNull List<? extends Module> getJsonModules();
}
