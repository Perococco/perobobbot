package perobobbot.data.domain;

import lombok.*;
import perobobbot.data.com.Extension;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "EXTENSION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class ExtensionEntity extends PersistentObjectWithUUID {

    @Column(name = "NAME", nullable = false, unique = true)
    @NotBlank
    private String name = "";

    @Column(name = "ACTIVATED", nullable = false)
    private boolean activated = false;

    @Column(name = "AVAILABLE", nullable = false)
    private boolean available = true;

    public ExtensionEntity(@NonNull @NotBlank String name) {
        super(UUID.randomUUID());
        this.name = name;
    }

    public @NonNull Extension toView() {
        return Extension.builder()
                        .name(name)
                        .id(getUuid())
                        .available(available)
                        .activated(activated)
                        .build();
    }

    public boolean isActiveAndAvailable() {
        return available && activated;
    }

    public boolean hasName(@NonNull String name) {
        return name.equals(this.name);
    }
}
