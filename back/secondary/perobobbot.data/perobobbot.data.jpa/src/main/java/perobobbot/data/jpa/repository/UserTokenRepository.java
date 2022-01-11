package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.com.UnknownUserToken;
import perobobbot.data.domain.UserTokenEntity;
import perobobbot.lang.Platform;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface UserTokenRepository extends JpaRepository<UserTokenEntity, Long> {

    long countDistinctByPlatformUser_Platform(@NonNull Platform platform);

    @NonNull Stream<UserTokenEntity> findByOwner_LoginAndPlatformUser_PlatformAndScopesContains(@NonNull String login, @NonNull Platform platform, @NonNull String scope);

    @NonNull Stream<UserTokenEntity> findByOwner_LoginAndPlatformUser_Platform(@NonNull String login, @NonNull Platform platform);

    @NonNull Optional<UserTokenEntity> findByOwner_LoginAndPlatformUser_UserIdAndPlatformUser_Platform(@NonNull String login,
                                                                                                       @NonNull String userId,
                                                                                                       @NonNull Platform platform);

    @NonNull Stream<UserTokenEntity> findByPlatformUser_UserIdAndPlatformUser_Platform(@NonNull String userId, @NonNull Platform platform);

    @NonNull Stream<UserTokenEntity> findByPlatformUser_UserIdAndPlatformUser_PlatformAndScopesContains(@NonNull String viewerId, @NonNull Platform platform, @NonNull String scope);

    @NonNull Optional<UserTokenEntity> findByOwner_LoginAndMainIsTrueAndPlatformUser_Platform(@NonNull String login, @NonNull Platform platform);

    @NonNull Optional<UserTokenEntity> findByOwner_LoginAndMainIsTrueAndPlatformUser_PlatformAndScopesContains(@NonNull String login, @NonNull Platform platform, @NonNull String scope);

    @NonNull Optional<UserTokenEntity> findByUuid(@NonNull UUID uuid);

    @NonNull List<UserTokenEntity> findAllByOwner_Login(@NonNull String ownerLogin);

    default @NonNull UserTokenEntity getByUuid(@NonNull UUID uuid) {
        return findByUuid(uuid).orElseThrow(() -> new UnknownUserToken(uuid));
    }


}
