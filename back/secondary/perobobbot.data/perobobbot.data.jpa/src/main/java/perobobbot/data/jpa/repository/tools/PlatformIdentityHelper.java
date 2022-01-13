package perobobbot.data.jpa.repository.tools;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.domain.PlatformUserEntity;
import perobobbot.data.jpa.repository.PlatformUserRepositoryBase;
import perobobbot.lang.Platform;
import perobobbot.lang.UserIdentity;
import perobobbot.lang.fp.Function1;

@RequiredArgsConstructor
@Getter
public class PlatformIdentityHelper<I extends UserIdentity, T extends PlatformUserEntity<I>> {

    private final @NonNull I userIdentity;
    private final @NonNull PlatformUserRepositoryBase<I,T> platformUserRepository;
    private final @NonNull Function1<? super I, ? extends T> platformUserEntityFactory;

    public T updateIdentity() {
        final var platformUser = platformUserRepository.getByUserId(userIdentity.getUserId());
        platformUser.update(userIdentity);
        platformUserRepository.save(platformUser);
        return platformUser;
    }

    public @NonNull Platform getPlatform() {
        return userIdentity.getPlatform();
    }

    public @NonNull T getOrCreatePlatformUser() {
        return platformUserRepository.findByUserId(userIdentity.getUserId())
                                     .orElseGet(this::createAndSaveEntity);

    }

    private @NonNull T createAndSaveEntity() {
        final T entity = platformUserEntityFactory.apply(userIdentity);
        return platformUserRepository.save(entity);
    }
}
