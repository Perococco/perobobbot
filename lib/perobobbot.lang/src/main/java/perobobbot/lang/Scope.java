package perobobbot.lang;

import com.google.common.collect.ImmutableCollection;
import lombok.NonNull;

import java.util.stream.Collectors;

public interface Scope {

    @NonNull String getName();

    static String scopeNamesSpaceSeparated(@NonNull ImmutableCollection<? extends Scope> scopes) {
        return scopes.stream().map(Scope::getName).collect(Collectors.joining(" "));
    }

}
