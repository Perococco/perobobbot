package perobobbot.data.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.User;
import perobobbot.lang.ListTool;
import perobobbot.lang.RandomString;
import perobobbot.lang.fp.Function1;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Perococco
 */
@Entity
@Table(name = "USER", uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER__LOGIN", columnNames = {"LOGIN"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class UserEntity extends SimplePersistentObject {

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

    @ManyToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name=("FK_USER_ROLE__USER")))},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", foreignKey = @ForeignKey(name=("FK_USER_ROLE__ROLE")))}
    )
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    @Fetch(FetchMode.JOIN)
    private List<BotEntity> bots = new ArrayList<>();

    public UserEntity(
            @NonNull @Email String login,
            @NonNull @NotBlank String password) {
        this.login = login;
        this.password = password;
        this.jwtClaim = RandomString.generate(16);
    }

    public static UserEntity create(CreateUserParameters parameters) {
        return new UserEntity(parameters.getLogin(), parameters.getPassword());
    }

    public void regenerateJwtClaim() {
        this.jwtClaim = RandomString.generate(16);
    }

    @NonNull
    public <T> ImmutableList<T> transformedUserRoles(Function1<? super RoleEntity, ? extends T> transformer) {
        return roles.stream().map(transformer).collect(ListTool.collector());
    }

    @NonNull
    public Stream<RoleEntity> roleStream() {
        return roles.stream();
    }

    @NonNull
    public UserEntity addRole(@NonNull RoleEntity role) {
        if (!hasRole(role)) {
            this.roles.add(role);
        }
        return this;
    }

    @NonNull
    public UserEntity removeRole(@NonNull RoleEntity role) {
        this.roles.remove(role);
        return this;
    }

    private boolean hasRole(RoleEntity role) {
        return roles.contains(role);
    }

    public <T> ImmutableSet<T> allRoles(@NonNull Function1<? super RoleEntity, ? extends T> mapper) {
        return roles.stream().map(mapper).collect(ImmutableSet.toImmutableSet());
    }

    public @NonNull BotEntity createBot(@NonNull String botName) {
        final var bot = new BotEntity(this,botName);
        this.bots.add(bot);
        return bot;
    }

    public @NonNull User toView() {
        return User.builder()
                   .login(this.login)
                   .password(this.password)
                   .jwtClaim(this.jwtClaim)
                   .roles(this.roles.stream()
                                    .map(RoleEntity::toView)
                                    .collect(ImmutableSet.toImmutableSet()))
                   .build();
    }
}
