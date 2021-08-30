package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import perobobbot.data.com.UnknownExtension;
import perobobbot.data.domain.ExtensionEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author perococco
 */
public interface ExtensionRepository extends JpaRepository<ExtensionEntity, Long> {

    @NonNull ExtensionEntity getByUuid(@NonNull UUID id);

    @NonNull Optional<ExtensionEntity> findByName(@NonNull String name);

    @NonNull Stream<ExtensionEntity> findAllByAvailableIsTrue();

    default @NonNull ExtensionEntity getByName(@NonNull String extensionName) {
        return findByName(extensionName).orElseThrow(() -> new UnknownExtension(extensionName));
    }

    @Modifying
    @Query("update ExtensionEntity ex set ex.available = false")
    void setAllExtensionAsUnavailable();
}
