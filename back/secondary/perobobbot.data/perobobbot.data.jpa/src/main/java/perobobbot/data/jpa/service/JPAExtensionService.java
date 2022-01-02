package perobobbot.data.jpa.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perobobbot.data.com.BotExtension;
import perobobbot.data.com.Extension;
import perobobbot.data.com.UpdateBotExtensionParameters;
import perobobbot.data.com.UpdateExtensionParameters;
import perobobbot.data.domain.BotEntity;
import perobobbot.data.domain.BotExtensionEntity;
import perobobbot.data.domain.ExtensionEntity;
import perobobbot.data.jpa.repository.BotExtensionRepository;
import perobobbot.data.jpa.repository.BotRepository;
import perobobbot.data.jpa.repository.ExtensionRepository;
import perobobbot.data.service.ExtensionService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.Bot;
import perobobbot.lang.fp.Function2;

import java.util.UUID;

@Service
@UnsecuredService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JPAExtensionService implements ExtensionService {

    private final @NonNull ExtensionRepository extensionRepository;
    private final @NonNull BotExtensionRepository botExtensionRepository;
    private final @NonNull BotRepository botRepository;

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
    public void setAllExtensionAsUnavailable() {
        extensionRepository.setAllExtensionAsUnavailable();
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
        return toggleActivateState(extensionName, true);
    }

    @Override
    @Transactional
    public boolean deactivateExtension(@NonNull String extensionName) {
        return toggleActivateState(extensionName, false);
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
    public @NonNull ImmutableList<Extension> listAvailableExtensions() {
        return extensionRepository.findAllByAvailableIsTrue()
                                  .map(ExtensionEntity::toView)
                                  .collect(ImmutableList.toImmutableList());
    }

    @Override
    public @NonNull ImmutableList<Extension> listAllExtensions() {
        return extensionRepository.findAll()
                                  .stream()
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
        return botExtension != null && botExtension.isEnabled();
    }

    @Override
    @Transactional
    public @NonNull Extension updateExtension(@NonNull UUID extensionId, @NonNull UpdateExtensionParameters updateExtensionParameters) {
        final var extension = extensionRepository.getByUuid(extensionId);
        updateExtensionParameters.getActivatedAsOptional().ifPresent(extension::setActivated);

        return extensionRepository.save(extension).toView();
    }

    @Override
    public @NonNull ImmutableList<BotExtension> listAllBotExtensions(@NonNull UUID botId) {
        return botExtensionRepository.findByBot_Uuid(botId)
                                     .map(BotExtensionEntity::toView)
                                     .collect(ImmutableList.toImmutableList());
    }


    @Override
    @Transactional
    public @NonNull BotExtension updateBotExtension(@NonNull UUID botId, @NonNull UUID extensionId, @NonNull UpdateBotExtensionParameters parameters) {
        final var botExtension = getOrCreateBotExtension(botId,extensionId);

        parameters.getEnabledAsOptional().ifPresent(botExtension::setEnabled);

        return botExtensionRepository.save(botExtension).toView();
    }

    private @NonNull BotExtensionEntity getOrCreateBotExtension(@NonNull UUID botId, @NonNull UUID extensionId) {
        {
            final var existing = botExtensionRepository.findByBot_UuidAndExtension_Uuid(botId, extensionId).orElse(null);
            if (existing != null) {
                return existing;
            }
        }

        final var bot = botRepository.getByUuid(botId);
        final var extension = extensionRepository.getByUuid(extensionId);
        return bot.addExtension(extension);




    }


    @Override
    public @NonNull ImmutableList<BotExtension> listAllUserExtensions(@NonNull String login) {
        final @NonNull Function2<Bot,Extension,BotExtension> botExtensionFactory;
        {
            final var botExtensions = botExtensionRepository.findByBot_Owner_Login(login)
                                                            .map(BotExtensionEntity::toView)
                                                            .collect(ImmutableTable.toImmutableTable(BotExtension::getBot, BotExtension::getExtension, e -> e));
            botExtensionFactory = createBotExtensionFactory(botExtensions);
        }

        final var extensions = this.extensionRepository.findAllByAvailableIsTrue().map(ExtensionEntity::toView).toList();
        final var bots = this.botRepository.findAllByOwnerLogin(login).map(BotEntity::toView).toList();

        return extensions.stream()
                         .flatMap(ex -> bots.stream().map(botExtensionFactory.f2(ex)))
                         .collect(ImmutableList.toImmutableList());

    }




    private Function2<Bot,Extension,BotExtension> createBotExtensionFactory(@NonNull Table<Bot,Extension, BotExtension> existingBotExtensions) {
        return (bot, extension) -> {
            var existing = existingBotExtensions.get(bot,extension);
            if (existing != null) {
                return existing;
            }
            return new BotExtension(bot,extension,false);
        };
    }

}
