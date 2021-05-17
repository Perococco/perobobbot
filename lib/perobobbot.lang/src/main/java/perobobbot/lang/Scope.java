package perobobbot.lang;

import com.google.common.collect.ImmutableCollection;
import lombok.NonNull;
import perococco.perobobbot.common.lang.BasicScope;

import java.util.stream.Collectors;

public interface Scope {

    @NonNull String getName();

    static String scopeNamesSpaceSeparated(@NonNull ImmutableCollection<? extends Scope> scopes) {
        return scopes.stream().map(Scope::getName).collect(Collectors.joining(" "));
    }

    static @NonNull Scope basic(@NonNull String name) {
        return new BasicScope(name);
    }

}
