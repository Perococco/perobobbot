package perobobbot.data.domain;

import lombok.*;
import perobobbot.data.com.Extension;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "EXTENSION", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@Getter
public class ExtensionEntity extends PersistentObjectWithUUID {

    @Column(name = "NAME", nullable = false)
    @NotBlank
    private String name = "";

    @Column(name = "ACTIVATED", nullable = false)
    @Setter
    private boolean activated = false;

    @Column(name = "AVAILABLE", nullable = false)
    @Setter
    private boolean available = true;

    public ExtensionEntity(@NonNull @NotBlank String name) {
        super(UUID.randomUUID());
        this.name = name;
    }

    public @NonNull Extension toView() {
        return Extension.builder().name(name).activated(activated).build();
    }
}
