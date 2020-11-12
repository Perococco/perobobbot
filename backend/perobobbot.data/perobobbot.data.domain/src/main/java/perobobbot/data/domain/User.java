package perobobbot.data.domain;

import com.google.common.collect.ImmutableList;
import lombok.*;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.lang.ListTool;
import perobobbot.lang.RandomString;
import perobobbot.lang.fp.Function1;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Perococco
 */
@Entity
@Table(name = "USER", uniqueConstraints = {
        @UniqueConstraint(name = "UK__USER__LOGIN", columnNames = {"LOGIN"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class User extends SimplePersistentObject {

    @NonNull
    @NotBlank
    @Column(name="LOGIN")
    @Size(max = 255)
    private String login = "";

    @NonNull
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "JWT_CLAIM", nullable = false)
    private String jwtClaim = "";

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name=("FK_USER_ROLE__USER")))},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", foreignKey = @ForeignKey(name=("FK_USER_ROLE__ROLE")))}
    )
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private Set<Role> roles = new HashSet<>();

    public User(
            @NonNull @Email String login,
            @NonNull @NotBlank String password) {
        this.login = login;
        this.password = password;
        this.jwtClaim = RandomString.generate(16);
    }

    public static User create(CreateUserParameters parameters) {
        return new User(parameters.getLogin(),parameters.getPassword());
    }

    public void regenerateJwtClaim() {
        this.jwtClaim = RandomString.generate(16);
    }

    @NonNull
    public <T> ImmutableList<T> transformedUserRoles(Function1<? super Role, ? extends T> transformer) {
        return roles.stream().map(transformer).collect(ListTool.collector());
    }

    @NonNull
    public Stream<Role> roleStream() {
        return roles.stream();
    }

    @NonNull
    public User addRole(@NonNull Role role) {
        if (!hasRole(role)) {
            this.roles.add(role);
        }
        return this;
    }

    @NonNull
    public User removeRole(@NonNull Role role) {
        this.roles.remove(role);
        return this;
    }

    private boolean hasRole(Role role) {
        return roles.contains(role);
    }

}
