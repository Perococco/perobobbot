package perobobbot.security.com;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

@Value
@TypeScript
public class Role {

    @NonNull RoleKind roleKind;

    @NonNull ImmutableSet<Operation> operations;

}
