package perobobbot.data.com;

import lombok.NonNull;
import perobobbot.lang.DTO;


@DTO
public enum Operation {
    ;

    public @NonNull String getGrantedAuthorityName() {
        return "OP_" + this.name();
    }
}
