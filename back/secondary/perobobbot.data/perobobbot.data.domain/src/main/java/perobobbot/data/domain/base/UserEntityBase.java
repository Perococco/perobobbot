package perobobbot.data.domain.base;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import perobobbot.data.domain.BotEntity;
import perobobbot.data.domain.RoleEntity;
import perobobbot.data.domain.UserTokenEntity;
import perobobbot.data.domain.converter.LocaleConverter;
import perobobbot.lang.RandomString;
import perobobbot.persistence.SimplePersistentObject;
import perobobbot.security.com.Identification;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author perococco
 */
@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "login", callSuper = false)
public class UserEntityBase extends SimplePersistentObject {

    @NonNull
    @NotBlank
    @Column(name = "LOGIN",unique = true)
    @Size(max = 255)
    private String login = "";

    @Column(name = "DEACTIVATED", nullable = false)
    private boolean deactivated = false;

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
    private List<UserTokenEntity> userTokens = new ArrayList<>();

    @Column(name = "LOCALE")
    @Convert(converter = LocaleConverter.class)
    private Locale locale = Locale.ENGLISH;

    @OneToMany(mappedBy = "owner")
    @Fetch(FetchMode.JOIN)
    private List<BotEntity> bots = new ArrayList<>();

    @Embedded
    private UserIdentification identification;

    public UserEntityBase(
            @NonNull @Email String login,
            @NonNull Identification identification) {
        this.login = login;
        this.identification = new UserIdentification(identification);
        this.jwtClaim = RandomString.generate(16);
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

}
