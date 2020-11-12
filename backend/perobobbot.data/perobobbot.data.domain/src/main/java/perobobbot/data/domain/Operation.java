package perobobbot.data.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "OPERATION", uniqueConstraints = {@UniqueConstraint(name = "UK_OPERATION__NAME",columnNames = {"NAME"})})
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false,of = "name")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Operation extends SimplePersistentObject {

    @NonNull
    @Column(name = "NAME")
    @NotBlank
    String name = "";

    public Operation(@NonNull String name) {
        this.name = name;
    }


}
