package perobobbot.data.com;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;

@Value
public class Role {

    @NonNull String name;

    @NonNull ImmutableSet<Operation> operations;

}
