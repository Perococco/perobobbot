package perobobbot.data.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

/**
 * @author Perococco
 */
@Entity
@Table(name = "ROLE", uniqueConstraints = {@UniqueConstraint(name = "UK_ROLE__NAME",columnNames = {"NAME"})})
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false,of = "name")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends SimplePersistentObject {

    @NonNull
    @Column(name = "NAME")
    @NotBlank
    String name = "";

    public Role(@NonNull String name) {
        this.name = name;
    }

}
