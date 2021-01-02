package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import perobobbot.data.com.UnknownUser;
import perobobbot.data.domain.ExtensionEntity;
import perobobbot.data.domain.UserEntity;

import java.security.cert.Extension;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Perococco
 */
@Repository
public interface ExtensionRepository extends JpaRepository<ExtensionEntity, Long> {

    @NonNull Optional<ExtensionEntity> findByName(@NonNull String name);

    @NonNull Stream<ExtensionEntity> findAllByAvailableIsTrue();
}
