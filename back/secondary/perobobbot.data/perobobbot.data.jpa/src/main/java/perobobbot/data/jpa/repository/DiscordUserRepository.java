package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.Query;
import perobobbot.data.domain.DiscordUserEntity;
import perobobbot.lang.DiscordIdentity;
import perobobbot.lang.Platform;

import java.util.List;

public interface DiscordUserRepository extends PlatformUserRepositoryBase<DiscordIdentity,DiscordUserEntity> {

    @Query("""
            select d from DiscordUserEntity as d
            where (d.userId = :userInfo or d.login = :userInfo)
            """)
    @NonNull List<DiscordUserEntity> findAllFromUserInfo(@NonNull String userInfo);


    @Override
    @NonNull
    default Platform getPlatform() {
        return Platform.DISCORD;
    }

}
