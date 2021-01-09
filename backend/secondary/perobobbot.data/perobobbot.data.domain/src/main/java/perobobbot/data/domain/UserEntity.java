package perobobbot.data.domain;

import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.User;
import perobobbot.data.domain.base.UserEntityBase;
import perobobbot.lang.Platform;
import perobobbot.lang.RandomString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends UserEntityBase {

    public UserEntity(@NonNull @Email String login, @NonNull @NotBlank String password) {
        super(login, password);
    }

    public @NonNull BotEntity createBot(@NonNull String botName) {
        final var bot = new BotEntity(this, botName);
        this.getBots().add(bot);
        return bot;
    }

    public static @NonNull UserEntity create(CreateUserParameters parameters) {
        return new UserEntity(parameters.getLogin(), parameters.getPassword());
    }

    /**
     * Add a credential to the user without any password or nick
     * @param platform the platform the credential is associated to
     * @return the created entity
     */
    public @NonNull CredentialEntity addCredential(@NonNull Platform platform) {
        final var credential = new CredentialEntity(this, platform);
        this.getCredentials().add(credential);
        return credential;
    }

    /**
     * Regenerate the JWT claim. This will invalidate all
     * jwt tokens.
     */
    public void regenerateJwtClaim() {
        this.setJwtClaim(RandomString.generate(16));
    }

    /**
     * Add a role to this user
     * @param role the role to add
     * @return this user updated by adding the provided role
     */
    public @NonNull UserEntity addRole(@NonNull RoleEntity role) {
        if (!hasRole(role)) {
            this.getRoles().add(role);
        }
        return this;
    }

    /**
     * @param role the role to remove from this user
     * @return this {@link UserEntity} updated by removing the provided role
     */
    public @NonNull UserEntity removeRole(@NonNull RoleEntity role) {
        this.getRoles().remove(role);
        return this;
    }


    public @NonNull User toView() {
        return User.builder()
                   .login(this.getLogin())
                   .password(this.getPassword())
                   .jwtClaim(this.getJwtClaim())
                   .roles(this.roles().map(RoleEntity::getRole).collect(ImmutableSet.toImmutableSet()))
                   .operations(this.roles().flatMap(RoleEntity::allowedOperations).collect(ImmutableSet.toImmutableSet()))
                   .build();
    }

}
