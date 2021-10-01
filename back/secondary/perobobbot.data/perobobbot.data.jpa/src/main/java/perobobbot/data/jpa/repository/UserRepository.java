package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import perobobbot.data.com.UnknownUser;
import perobobbot.data.domain.UserEntity;
import perobobbot.lang.Platform;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author perococco
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLogin(@NonNull String login);

    default UserEntity getByLogin(@NonNull String login) {
        return findByLogin(login).orElseThrow(() -> new UnknownUser(login));
    }

    default boolean doesUserExist(@NonNull String login) {
        return findByLogin(login).isPresent();
    }

    @Query(nativeQuery = true,
            value = """
                    select
                    u.LOGIN as login,
                    u.IDENTIFICATION_MODE as identificationMode,
                    u.OPENID_PLATFORM as openIdPlatform,
                    u.PASSWORD as password,
                    u.DEACTIVATED as deactivated,
                    u.LOCALE as locale,
                    u.JWT_CLAIM as jwtClaim,
                    r.ROLE as roleKind,
                    ro.OPERATION as operation
                    from PEROBOBBOT.USER as u
                    left join PEROBOBBOT.USER_ROLE as ur on ur.USER_ID = u.ID
                    left join PEROBOBBOT.ROLE as r on r.ID = ur.ROLE_ID
                    left join PEROBOBBOT.ROLE_OPERATION as ro on ro.ROLE_ID = r.ID
                    where u.login = :login
                    """)
    List<UserDetailProjection> getUserDetail(String login);


    @Query(value = """
            select u.login
            from UserEntity u
            join UserTokenEntity ur on ur.owner = u
            join ViewerIdentityEntity vi on vi = ur.viewerIdentity
            WHERE vi.platform = :platform AND vi.viewerId = :viewerId
            """)
    @NonNull Stream<String> getLoginOfUsersAuthenticatedWithViewerId(@NonNull Platform platform, @NonNull String viewerId);
}
