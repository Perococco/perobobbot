package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import perobobbot.data.com.UnknownBot;
import perobobbot.data.domain.BotEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author perococco
 */
@Repository
public interface BotRepository extends JpaRepository<BotEntity, Long> {

    void deleteByUuid(@NonNull UUID uuid);

    @NonNull Optional<BotEntity> findByUuid(@NonNull UUID uuid);

    default @NonNull BotEntity getByUuid(@NonNull UUID uuid) {
        return findByUuid(uuid).orElseThrow(() -> new UnknownBot(uuid));
    }

    @Query("select b from BotEntity as b where b.owner.login = :login")
    @NonNull Stream<BotEntity> findAllByOwnerLogin(@NonNull @Param("login") String login);

}
