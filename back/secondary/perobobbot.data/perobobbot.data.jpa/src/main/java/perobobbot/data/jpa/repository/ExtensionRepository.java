package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import perobobbot.data.com.UnknownExtension;
import perobobbot.data.domain.ExtensionEntity;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author perococco
 */
@Repository
public interface ExtensionRepository extends JpaRepository<ExtensionEntity, Long> {

    @NonNull Optional<ExtensionEntity> findByName(@NonNull String name);

    @NonNull Stream<ExtensionEntity> findAllByAvailableIsTrue();

    default @NonNull ExtensionEntity getByName(@NonNull String extensionName) {
        return findByName(extensionName).orElseThrow(() -> new UnknownExtension(extensionName));
    }
}
