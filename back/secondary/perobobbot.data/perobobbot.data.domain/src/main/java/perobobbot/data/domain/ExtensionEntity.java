package perobobbot.data.domain;

import lombok.*;
import perobobbot.data.com.Extension;
import perobobbot.data.domain.base.ExtensionEntityBase;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "EXTENSION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExtensionEntity extends ExtensionEntityBase {

    public ExtensionEntity(@NonNull @NotBlank String name) {
        super(name);
    }

    public @NonNull Extension toView() {
        return Extension.builder().name(getName()).activated(isActivated()).build();
    }

    public boolean isActiveAndAvailable() {
        return isAvailable() && isActivated();
    }
}
