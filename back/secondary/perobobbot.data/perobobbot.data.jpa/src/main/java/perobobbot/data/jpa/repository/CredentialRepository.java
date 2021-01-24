package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.com.UnknownCredential;
import perobobbot.data.domain.CredentialEntity;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.UUID;

public interface CredentialRepository extends JpaRepository<CredentialEntity,Long> {

    @NonNull Optional<CredentialEntity> findByOwner_LoginAndPlatform(@NonNull String userLogin, @NonNull Platform platform);

    @NonNull Optional<CredentialEntity> findByOwner_LoginAndPlatformAndNick(@NonNull String userLogin, @NonNull Platform platform, @NonNull String nick);

    @NonNull Optional<CredentialEntity> findByUuid(@NonNull UUID uuid);

    default @NonNull CredentialEntity getByUuid(@NonNull UUID uuid) {
        return findByUuid(uuid).orElseThrow(() -> new UnknownCredential(uuid));
    }
}
