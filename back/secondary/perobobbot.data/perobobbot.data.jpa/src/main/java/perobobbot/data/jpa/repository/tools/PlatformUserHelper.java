package perobobbot.data.jpa.repository.tools;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.domain.DiscordUserEntity;
import perobobbot.data.domain.PlatformUserEntity;
import perobobbot.data.domain.TwitchUserEntity;
import perobobbot.data.jpa.repository.DiscordUserRepository;
import perobobbot.data.jpa.repository.PlatformUserRepositoryBase;
import perobobbot.data.jpa.repository.TwitchUserRepository;
import perobobbot.lang.*;

@RequiredArgsConstructor
public class PlatformUserHelper {

    private final @NonNull DiscordUserRepository discordUserRepository;
    private final @NonNull TwitchUserRepository twitchUserRepository;

    private final UserIdentity.Visitor<PlatformIdentityHelper<? extends UserIdentity, ? extends PlatformUserEntity<?>>> visitor = new UserIdentity.Visitor<>() {
        @Override
        public @NonNull PlatformIdentityHelper<? extends UserIdentity, ? extends PlatformUserEntity<?>> visit(@NonNull TwitchIdentity twitchIdentity) {
            return new PlatformIdentityHelper<>(twitchIdentity, twitchUserRepository, TwitchUserEntity::new);
        }

        @Override
        public @NonNull PlatformIdentityHelper<? extends UserIdentity, ? extends PlatformUserEntity<?>> visit(@NonNull DiscordIdentity discordIdentity) {
            return new PlatformIdentityHelper<>(discordIdentity, discordUserRepository, DiscordUserEntity::new);
        }
    };

    public @NonNull PlatformIdentityHelper<? extends UserIdentity, ? extends PlatformUserEntity<?>> helperForIdentity(@NonNull UserIdentity identity) {
        return identity.accept(this.visitor);
    }

    public @NonNull PlatformUserRepositoryBase<?,?> repositoryForPlatform(@NonNull Platform platform) {
        return switch (platform) {
            case DISCORD -> discordUserRepository;
            case TWITCH -> twitchUserRepository;
            default -> Todo.TODO();
        };
    }
}
