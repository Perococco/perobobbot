package perobobbot.data.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.DuplicateCredentialForUser;
import perobobbot.data.com.DuplicateRoleForUser;
import perobobbot.data.com.User;
import perobobbot.lang.Credential;
import perobobbot.lang.ListTool;
import perobobbot.lang.Platform;
import perobobbot.lang.RandomString;
import perobobbot.lang.fp.Function1;
import perobobbot.persistence.SimplePersistentObject;

import javax.persistence.*;
import javax.swing.text.PlainDocument;
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
        @UniqueConstraint(columnNames = {"LOGIN"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(of = "login", callSuper = false)
public class UserEntity extends SimplePersistentObject {

    @NonNull
    @NotBlank
    @Column(name = "LOGIN")
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
            uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID","ROLE_ID"})},
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")}
    )
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private Set<CredentialEntity> credentials = new HashSet<>();

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

    public static @NonNull UserEntity create(CreateUserParameters parameters) {
        return new UserEntity(parameters.getLogin(), parameters.getPassword());
    }

    public @NonNull CredentialEntity addCredential(@NonNull Platform platform, @NonNull String nick) {
        final var credential = new CredentialEntity(this, platform, nick);
        if (credentials.contains(credential)) {
            throw new DuplicateCredentialForUser(this.login, platform, nick);
        }
        this.credentials.add(credential);
        return credential;
    }

    public void regenerateJwtClaim() {
        this.jwtClaim = RandomString.generate(16);
    }

    @NonNull
    public UserEntity addRole(@NonNull RoleEntity role) {
        if (hasRole(role)) {
            throw new DuplicateRoleForUser(this.login, role.getRole());
        }
        this.roles.add(role);
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

    public @NonNull Stream<RoleEntity> roles() {
        return roles.stream();
    }

    public @NonNull Stream<CredentialEntity> credentials() {
        return credentials.stream();
    }

    public @NonNull BotEntity createBot(@NonNull String botName) {
        final var bot = new BotEntity(this, botName);
        this.bots.add(bot);
        return bot;
    }

    public @NonNull User toView() {
        return User.builder()
                   .login(this.login)
                   .password(this.password)
                   .jwtClaim(this.jwtClaim)
                   .roles(this.roles.stream().map(RoleEntity::getRole).collect(ImmutableSet.toImmutableSet()))
                   .operations(this.roles.stream().flatMap(RoleEntity::allowedOperationStream).collect(ImmutableSet.toImmutableSet()))
                   .build();
    }
}
