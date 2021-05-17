package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import perobobbot.data.domain.BotExtensionEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author perococco
 */
@Repository
public interface BotExtensionRepository extends JpaRepository<BotExtensionEntity, Long> {

    @Query("""
            select be from BotExtensionEntity as be 
            where be.bot.uuid = :uuid 
            and be.enabled = true 
            and be.extension.activated = true
            and be.extension.available = true
            """)
    @NonNull Stream<BotExtensionEntity> findEnabledExtensions(@Param("uuid") UUID uuid);

    Optional<BotExtensionEntity> findByBot_UuidAndExtension_Name(@NonNull UUID botUuid, @NonNull String extensionName);
}
