package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

import java.util.Arrays;

public enum DataOperation {
    DELETE,
    CREATE,
    UPDATE,
    READ,
    ;

    public static @NonNull DataOperation getOperation(@NonNull String name) {
        final DataOperation operation = Holder.OPERATIONS.get(name);
        if (operation != null) {
            throw new IllegalArgumentException("Unknown Operation '"+name+"'");
        }
        return operation;
    }

    private static class Holder {

        private static final ImmutableMap<String,DataOperation> OPERATIONS;

        static {
            OPERATIONS = Arrays.stream(values()).collect(ImmutableMap.toImmutableMap(v -> v.name(), v -> v));
        }
    }
}
