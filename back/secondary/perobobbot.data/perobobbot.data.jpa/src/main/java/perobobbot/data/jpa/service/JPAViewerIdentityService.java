package perobobbot.data.jpa.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.domain.ViewerIdentityEntity;
import perobobbot.data.jpa.repository.ViewerIdentityRepository;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.ViewerIdentityService;
import perobobbot.lang.Platform;
import perobobbot.lang.ViewerIdentity;
import perobobbot.lang.fp.Function1;
import perobobbot.lang.fp.Predicate1;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@UnsecuredService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPAViewerIdentityService implements ViewerIdentityService {

    private final @NonNull ViewerIdentityRepository viewerIdentityRepository;

    @Override
    public @NonNull Optional<ViewerIdentity> findIdentity(@NonNull Platform platform, @NonNull String userInfo) {
        final var viewers = viewerIdentityRepository.findFromViewerInfo(platform, userInfo);

        final var findMatching = new Function1<Predicate1<ViewerIdentityEntity>, Optional<ViewerIdentityEntity>>() {
            @Override
            public @NonNull Optional<ViewerIdentityEntity> f(@NonNull Predicate1<ViewerIdentityEntity> tester) {
                return viewers.stream().filter(tester).findAny();
            }
        };


        return Stream.<Predicate1<ViewerIdentityEntity>>of(
                v -> v.getViewerId().equals(userInfo),
                v -> v.getLogin().equals(userInfo),
                v -> v.getPseudo().equalsIgnoreCase(userInfo))
                     .map(findMatching)
                     .flatMap(Optional::stream)
                     .map(ViewerIdentityEntity::toView)
                     .findFirst();

    }

    @Override
    @Transactional
    public @NonNull ViewerIdentity updateIdentity(@NonNull Platform platform, @NonNull String viewerId, @NonNull String newLogin, @NonNull String newPseudo) {
        {
            final var existing = viewerIdentityRepository.findByPlatformAndViewerId(platform, viewerId).orElse(null);

            if (existing != null) {
                existing.setLogin(newLogin);
                existing.setPseudo(newPseudo);
                return viewerIdentityRepository.save(existing).toView();
            }
        }

        final var identity = new ViewerIdentityEntity(platform, viewerId, newLogin, newPseudo);
        return viewerIdentityRepository.save(identity).toView();
    }

}
