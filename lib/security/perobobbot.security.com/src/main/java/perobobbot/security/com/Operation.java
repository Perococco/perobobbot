package perobobbot.security.com;

import lombok.NonNull;
import perobobbot.lang.IdentifiedEnum;
import perobobbot.lang.TypeScript;


@TypeScript
public enum Operation implements IdentifiedEnum {
    READ_CREDENTIALS
    ;


    public @NonNull String getGrantedAuthorityName() {
        return "OP_" + this.name();
    }

    @Override
    public @NonNull String getIdentification() {
        return name();
    }
}
