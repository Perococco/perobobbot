package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
public class ExtensionEntityBase extends PersistentObjectWithUUID {

    @Column(name = "NAME", nullable = false, unique = true)
    @NotBlank
    private String name = "";

    @Column(name = "ACTIVATED", nullable = false)
    private boolean activated = false;

    @Column(name = "AVAILABLE", nullable = false)
    private boolean available = true;

    public ExtensionEntityBase(@NonNull @NotBlank String name) {
        super(UUID.randomUUID());
        this.name = name;
    }

}
