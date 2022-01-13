package perobobbot.data.domain;

import com.google.common.collect.ImmutableSet;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.domain.converter.LocaleConverter;
import perobobbot.lang.RandomString;
import perobobbot.lang.token.EncryptedUserToken;
import perobobbot.persistence.SimplePersistentObject;
import perobobbot.security.com.Authentication;
import perobobbot.security.com.User;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Stream;

@Entity
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class UserEntity extends SimplePersistentObject {

    @NonNull
    @NotBlank
    @Column(name = "LOGIN",unique = true)
    @Size(max = 255)
    private String login = "";

    @Column(name = "DEACTIVATED", nullable = false)
    private boolean deactivated = false;

    @Column(name = "JWT_CLAIM", nullable = false)
    private String jwtClaim = "";

    @Column(name = "LOCALE")
    @Convert(converter = LocaleConverter.class)
    private Locale locale = Locale.ENGLISH;

    @Embedded
    private UserAuthentication authentication;


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

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private List<UserTokenEntity> userTokens = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    @Fetch(FetchMode.JOIN)
    private List<BotEntity> bots = new ArrayList<>();

    public UserEntity(
            @NonNull @Email String login,
            @NonNull Authentication authentication) {
        this.login = login;
        this.authentication = new UserAuthentication(authentication);
        this.jwtClaim = RandomString.createWithLength(16);
    }

    protected boolean hasRole(RoleEntity role) {
        return roles.contains(role);
    }

    public @NonNull Stream<RoleEntity> roles() {
        return roles.stream();
    }

    public @NonNull Stream<UserTokenEntity> credentials() {
        return userTokens.stream();
    }

    public @NonNull BotEntity createBot(@NonNull String botName) {
        final var bot = new BotEntity(this, botName);
        this.bots.add(bot);
        return bot;
    }

    public static @NonNull UserEntity create(CreateUserParameters parameters) {
        return new UserEntity(parameters.getLogin(), parameters.getAuthentication());
    }

    /**
     *
     * @param platformUser the identity of the viewer the token refers to
     * @param userToken the user token obtain by OAuth processs
     * @return the new entity representing the token for the provided viewer identity and owned by this
     */
    public @NonNull UserTokenEntity setUserToken(@NonNull PlatformUserEntity<?> platformUser, @NonNull EncryptedUserToken userToken) {
        platformUser.getUserToken().ifPresent(UserTokenEntity::detach);

        return new UserTokenEntity(this,platformUser,userToken);
    }

    void removeUserToken(@NonNull UUID tokenId) {
        this.userTokens.removeIf(t -> tokenId.equals(t.getUuid()));
    }


    public UserTwitchSubscriptionEntity subscribeTo(@NonNull TwitchSubscriptionEntity twitchSubscription) {
        return new UserTwitchSubscriptionEntity(this,twitchSubscription);
    }

    /**
     * Regenerate the JWT claim. This will invalidate all
     * jwt tokens.
     */
    public void regenerateJwtClaim() {
        this.jwtClaim = RandomString.createWithLength(16);
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
                   .deactivated(deactivated)
                   .login(login)
                   .locale(locale)
                   .authentication(authentication.toView())
                   .jwtClaim(jwtClaim)
                   .roles(this.roles().map(RoleEntity::getRole).collect(ImmutableSet.toImmutableSet()))
                   .operations(this.roles().flatMap(RoleEntity::allowedOperations).collect(ImmutableSet.toImmutableSet()))
                   .build();
    }

}
