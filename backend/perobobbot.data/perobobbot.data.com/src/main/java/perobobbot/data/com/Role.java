package perobobbot.data.com;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;

@Value
public class Role {

    @NonNull RoleKind roleKind;

    @NonNull ImmutableSet<Operation> operations;

}
