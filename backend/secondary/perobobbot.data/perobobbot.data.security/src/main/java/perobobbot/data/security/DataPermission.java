package perobobbot.data.security;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.lang.Parser;

import java.util.Arrays;

public enum DataPermission {
    DELETE,
    CREATE,
    UPDATE,
    READ,
    ;

    public static @NonNull DataPermission getOperation(@NonNull String name) {
        final DataPermission operation = Holder.OPERATIONS.get(name);
        if (operation == null) {
            throw new IllegalArgumentException("Unknown Operation '"+name+"'");
        }
        return operation;
    }

    public static final Parser<DataPermission> PARSER = Parser.with(DataPermission::getOperation,DataPermission.class);


    private static class Holder {

        private static final ImmutableMap<String, DataPermission> OPERATIONS;

        static {
            OPERATIONS = Arrays.stream(values()).collect(ImmutableMap.toImmutableMap(v -> v.name(), v -> v));
        }
    }
}
