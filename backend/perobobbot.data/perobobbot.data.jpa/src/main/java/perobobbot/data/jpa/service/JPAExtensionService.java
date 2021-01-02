package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.com.Extension;
import perobobbot.data.domain.BotExtensionEntity;
import perobobbot.data.domain.ExtensionEntity;
import perobobbot.data.jpa.repository.BotExtensionRepository;
import perobobbot.data.jpa.repository.ExtensionRepository;
import perobobbot.data.service.ExtensionService;
import perobobbot.data.service.UnsecuredService;

import java.util.HashSet;
import java.util.UUID;

@Service
@UnsecuredService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPAExtensionService implements ExtensionService {

    private final @NonNull ExtensionRepository extensionRepository;
    private final @NonNull BotExtensionRepository botExtensionRepository;

    @Override
    @Transactional
    public void updateExtensionList(@NonNull ImmutableSet<String> foundExtensionNames) {
        final var missing = new HashSet<>(foundExtensionNames);
        final var existing = extensionRepository.findAll();

        for (ExtensionEntity extension : existing) {
            boolean wasPresent = missing.remove(extension.getName());
            extension.setActivated(wasPresent);
        }

        missing.forEach(name -> existing.add(new ExtensionEntity(name)));

        extensionRepository.saveAll(existing);
    }

    @Override
    public @NonNull ImmutableList<Extension> listAllExtensions() {
        return extensionRepository.findAllByAvailableIsTrue()
                                  .map(ExtensionEntity::toView)
                                  .collect(ImmutableList.toImmutableList());
    }


    @Override
    public @NonNull ImmutableList<Extension> listEnabledExtensions(@NonNull UUID botId) {
        return botExtensionRepository.findEnabledExtensions(botId)
                                     .map(BotExtensionEntity::getExtension)
                                     .map(ExtensionEntity::toView)
                                     .collect(ImmutableList.toImmutableList());
    }

    @Override
    public boolean isExtensionEnabled(@NonNull UUID botId, @NonNull UUID extensionId) {
        return botExtensionRepository.findByBot_UuidAndExtension_Uuid(botId, extensionId)
                                     .filter(BotExtensionEntity::isEnabledAndExtensionActive)
                                     .isPresent();
    }
}
