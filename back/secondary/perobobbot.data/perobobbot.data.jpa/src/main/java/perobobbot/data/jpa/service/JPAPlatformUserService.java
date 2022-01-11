package perobobbot.data.jpa.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.domain.PlatformUserEntity;
import perobobbot.data.jpa.repository.PlatformUserRepository;
import perobobbot.data.jpa.repository.tools.PlatformUserHelper;
import perobobbot.data.service.PlatformUserService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.Platform;
import perobobbot.lang.PlatformUser;
import perobobbot.lang.UserIdentity;

import java.util.Optional;

@Service
@UnsecuredService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPAPlatformUserService implements PlatformUserService {

    private final @NonNull PlatformUserRepository platformUserRepository;
    private final @NonNull PlatformUserHelper platformUserHelper;

    @Override
    public @NonNull Optional<PlatformUser> findPlatformUser(@NonNull Platform platform, @NonNull String userInfo) {
        return platformUserHelper.repositoryForPlatform(platform)
                          .findFromUserInfo(userInfo)
                          .map(PlatformUserEntity::toView)
                          .findFirst();
    }

    @Override
    @Transactional
    public @NonNull PlatformUser updateUserIdentity(@NonNull UserIdentity newIdentity) {
        return platformUserHelper.helperForIdentity(newIdentity).updateIdentity().toView();
    }


}
