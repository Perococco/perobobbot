package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.com.UnknownUserToken;
import perobobbot.data.domain.UserEntity;
import perobobbot.data.domain.UserTokenEntity;
import perobobbot.lang.Platform;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface UserTokenRepository extends JpaRepository<UserTokenEntity,Long> {

    @NonNull Stream<UserTokenEntity> findByOwner_LoginAndViewerIdentity_PlatformAndScopesContains(@NonNull String login, @NonNull Platform platform, @NonNull String scope);
    @NonNull Stream<UserTokenEntity> findByOwner_LoginAndViewerIdentity_Platform(@NonNull String login, @NonNull Platform platform);

    @NonNull Optional<UserTokenEntity> findByOwner_LoginAndViewerIdentity_ViewerIdAndViewerIdentity_Platform(@NonNull String login,
                                                                                                             @NonNull String viewerId,
                                                                                                             @NonNull Platform platform);

    @NonNull Optional<UserTokenEntity> findByOwner_LoginAndMainIsTrueAndViewerIdentity_Platform(@NonNull String login, @NonNull Platform platform);
    @NonNull Optional<UserTokenEntity> findByOwner_LoginAndMainIsTrueAndViewerIdentity_PlatformAndScopesContains(@NonNull String login, @NonNull Platform platform, @NonNull String scope);

    @NonNull Optional<UserTokenEntity> findByUuid(@NonNull UUID uuid);

    @NonNull List<UserTokenEntity> findAllByOwner_Login(@NonNull String ownerLogin);

    default @NonNull UserTokenEntity getByUuid(@NonNull UUID uuid) {
        return findByUuid(uuid).orElseThrow(() -> new UnknownUserToken(uuid));
    }



}
