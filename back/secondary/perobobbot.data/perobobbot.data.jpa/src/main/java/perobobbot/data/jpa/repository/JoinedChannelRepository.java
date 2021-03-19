package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.domain.BotEntity;
import perobobbot.data.domain.JoinedChannelEntity;
import perobobbot.lang.Platform;

import java.util.Optional;
import java.util.stream.Stream;

public interface JoinedChannelRepository extends JpaRepository<JoinedChannelEntity, Long> {

    @NonNull Optional<JoinedChannelEntity> findByBotAndPlatformAndChannelName(@NonNull BotEntity botEntity,
                                                                              @NonNull Platform platform,
                                                                              @NonNull String channelName);

    @NonNull Stream<JoinedChannelEntity> findByPlatform(@NonNull Platform platform);
}
