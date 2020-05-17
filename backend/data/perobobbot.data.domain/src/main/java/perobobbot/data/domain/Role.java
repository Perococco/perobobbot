package perobobbot.data.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @author Bastien Aracil
 * @version 15/04/2019
 */
@Entity
@Table(name = "ROLE")
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false,of = "name")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends SimplePersistentObject {

    @NonNull
    @Column(name = "NAME",unique = true)
    @NotBlank
    String name = "";

    public Role(@NonNull String name) {
        this.name = name;
    }

}
