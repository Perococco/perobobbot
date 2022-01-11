package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.Query;
import perobobbot.data.domain.TwitchUserEntity;
import perobobbot.lang.Platform;
import perobobbot.lang.TwitchIdentity;

import java.util.List;
import java.util.stream.Stream;

public interface TwitchUserRepository extends PlatformUserRepositoryBase<TwitchIdentity, TwitchUserEntity> {


    @Query("""
            select v from TwitchUserEntity as v
            where (v.userId = :userInfo or UPPER(v.pseudo) = UPPER(:userInfo) or v.login = :userInfo) 
            """)
    @NonNull Stream<TwitchUserEntity> findFromUserInfo(@NonNull String userInfo);

    @Override
    default @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }
}
