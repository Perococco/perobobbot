package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import perobobbot.data.domain.JoinedChannelEntity;
import perobobbot.lang.Platform;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JoinedChannelRepository extends JpaRepository<JoinedChannelEntity, Long> {


    @NonNull Optional<JoinedChannelEntity> findByUuid(@NonNull UUID channelId);

    @NonNull List<JoinedChannelEntity> findAllByViewerIdentity_Platform(@NonNull Platform platform);

}
