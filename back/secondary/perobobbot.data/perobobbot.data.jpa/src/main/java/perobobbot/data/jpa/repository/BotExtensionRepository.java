package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perobobbot.data.domain.BotExtensionEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author perococco
 */
public interface BotExtensionRepository extends JpaRepository<BotExtensionEntity, Long> {

    @Query("""
            select be from BotExtensionEntity as be
            where be.bot.uuid = :uuid
            and be.enabled = true
            and be.extension.activated = true
            and be.extension.available = true
            """)
    @NonNull Stream<BotExtensionEntity> findEnabledExtensions(@Param("uuid") UUID uuid);

    @NonNull Optional<BotExtensionEntity> findByBot_UuidAndExtension_Name(@NonNull UUID botUuid, @NonNull String extensionName);

    @NonNull Stream<BotExtensionEntity> findByBot_Uuid(@NonNull UUID botUuid);

    @NonNull Stream<BotExtensionEntity> findByBot_Owner_Login(@NonNull String login);

    @NonNull Optional<BotExtensionEntity> findByBot_UuidAndExtension_Uuid(@NonNull UUID botId, @NonNull UUID extension);
}
