package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.com.Extension;
import perobobbot.data.domain.base.ExtensionEntityBase;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "EXTENSION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExtensionEntity extends ExtensionEntityBase {

    public ExtensionEntity(@NonNull @NotBlank String name) {
        super(name);
    }

    public @NonNull Extension toView() {
        return Extension.builder()
                        .name(getName())
                        .id(getUuid())
                        .available(isAvailable())
                        .activated(isActivated())
                        .build();
    }

    public boolean isActiveAndAvailable() {
        return isAvailable() && isActivated();
    }
}
