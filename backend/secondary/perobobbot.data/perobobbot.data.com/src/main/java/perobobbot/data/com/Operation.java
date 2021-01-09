package perobobbot.data.com;

import lombok.NonNull;
import perobobbot.lang.TypeScript;
import perobobbot.lang.IdentifiedEnum;


@TypeScript
public enum Operation implements IdentifiedEnum {
    ;


    public @NonNull String getGrantedAuthorityName() {
        return "OP_" + this.name();
    }

    @Override
    public @NonNull String getIdentification() {
        return name();
    }
}
