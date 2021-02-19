package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.com.UnknownCredential;
import perobobbot.data.domain.TokenEntity;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;

public interface CredentialRepository extends JpaRepository<TokenEntity,Long> {

    @NonNull Optional<TokenEntity> findByOwner_LoginAndPlatform(@NonNull String userLogin, @NonNull Platform platform);

    @NonNull Optional<TokenEntity> findByOwner_LoginAndPlatformAndNick(@NonNull String userLogin, @NonNull Platform platform, @NonNull String nick);

    @NonNull Optional<TokenEntity> findByUuid(@NonNull UUID uuid);

    default @NonNull TokenEntity getByUuid(@NonNull UUID uuid) {
        return findByUuid(uuid).orElseThrow(() -> new UnknownCredential(uuid));
    }
}
