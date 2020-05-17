package perobobbot.data.domain;

import com.google.common.collect.ImmutableList;
import lombok.*;
import perobobbot.common.lang.ListTool;
import perobobbot.common.lang.RandomString;
import perobobbot.common.lang.fp.Function1;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Bastien Aracil
 * @version 14/04/2019
 */
@Entity
@Table(name = "USER", uniqueConstraints = {
        @UniqueConstraint(name = "UK__USER__EMAIL", columnNames = {"EMAIL"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class User extends SimplePersistentObject {

    @NonNull
    @NotBlank
    @Column(name="LOGIN",unique = true)
    @Size(max = 255)
    private String login = "";

    @NonNull
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "JWT_CLAIM", nullable = false)
    private String jwtClaim = "";

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy ="user")
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private List<UserRole> roles = new ArrayList<>();

    public User(
            @NonNull @Email String login,
            @NonNull @NotBlank String password) {
        this.login = login;
        this.password = password;
        this.jwtClaim = RandomString.generate(16);
    }

    public void regenerateJwtClaim() {
        this.jwtClaim = RandomString.generate(16);
    }

    @NonNull
    public <T> ImmutableList<T> transformedUserRoles(Function1<? super UserRole, ? extends T> transformer) {
        return roles.stream().map(transformer).collect(ListTool.collector());
    }

    @NonNull
    public User addRole(@NonNull Role role) {
        if (!hasRole(role)) {
            final UserRole userRole = new UserRole(this, role);
            this.roles.add(userRole);
        }
        return this;
    }

    @NonNull
    public User removeRole(@NonNull Role role) {
        this.roles.removeIf(u -> u.getRole().equals(role));
        return this;
    }

    private boolean hasRole(Role role) {
        return roles.stream().anyMatch(ur -> ur.getRole().equals(role));
    }

}
