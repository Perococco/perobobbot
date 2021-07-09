package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
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
    public void setExtensionAvailable(@NonNull String extensionName) {
        final var extension = extensionRepository.findByName(extensionName)
                .orElseGet(() -> new ExtensionEntity(extensionName));

        extension.setAvailable(true);
        extensionRepository.save(extension);
    }

    @Override
    @Transactional
    public void setExtensionUnavailable(@NonNull String extensionName) {
        final var extension = extensionRepository.findByName(extensionName).orElse(null);

        if (extension != null) {
            extension.setAvailable(false);
            extensionRepository.save(extension);
        }
    }

    @Override
    @Transactional
    public boolean activateExtension(@NonNull String extensionName) {
        return toggleActivateState(extensionName,true);
    }

    @Override
    @Transactional
    public boolean deactivateExtension(@NonNull String extensionName) {
        return toggleActivateState(extensionName,false);
    }

    private boolean toggleActivateState(@NonNull String extensionName, boolean newState) {
        final var extension = extensionRepository.getByName(extensionName);
        if (newState == extension.isActivated()) {
            return false;
        }
        extension.setActivated(newState);
        extensionRepository.save(extension);
        return true;
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
    public boolean isExtensionEnabled(@NonNull UUID botId, @NonNull String extensionName) {
        final var extension = extensionRepository.findByName(extensionName).orElse(null);
        if (extension == null) {
            return false;
        }

        if (!extension.isActiveAndAvailable()) {
            return false;
        }

        final var botExtension = botExtensionRepository.findByBot_UuidAndExtension_Name(botId, extensionName).orElse(null);
        return botExtension == null || botExtension.isEnabled();
    }
}
