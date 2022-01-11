package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.domain.JoinedTwitchChannelEntity;
import perobobbot.lang.Platform;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JoinedChannelRepository extends JpaRepository<JoinedTwitchChannelEntity, Long> {


    @NonNull Optional<JoinedTwitchChannelEntity> findByUuid(@NonNull UUID channelId);

    @NonNull List<JoinedTwitchChannelEntity> findAllByTwitchUser_Platform(@NonNull Platform platform);

    @NonNull Optional<JoinedTwitchChannelEntity> findByBot_UuidAndTwitchUser_UuidAndChannelName(@NonNull UUID botId, @NonNull UUID viewerIdentityId, @NonNull String channelName);

}
