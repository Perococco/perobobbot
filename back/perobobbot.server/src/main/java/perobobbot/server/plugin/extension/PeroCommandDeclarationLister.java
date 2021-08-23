package perobobbot.server.plugin.extension;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.command.CommandController;
import perobobbot.command.CommandDeclaration;
import perobobbot.command.CommandDeclarationLister;
import perobobbot.command.ROCommandRegistry;
import perobobbot.data.service.EventService;
import perobobbot.data.service.ExtensionService;
import perobobbot.lang.Platform;
import perobobbot.lang.PluginService;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@PluginService(
        type = CommandDeclarationLister.class,
        apiVersion = CommandDeclarationLister.VERSION,
        sensitive = false)
@RequiredArgsConstructor
@Component
public class PeroCommandDeclarationLister implements CommandDeclarationLister {

    private final @NonNull
    @EventService
    ExtensionService extensionService;
    private final @NonNull ROCommandRegistry commandRegistry;
    private final @NonNull CommandController commandController;

    @Override
    public @NonNull ImmutableSet<CommandDeclaration> getAllActiveCommands(@NonNull UUID botId) {
        return new Execution(botId).getAllActiveCommands();
    }

    @Override
    public char getPrefix(@NonNull Platform platform) {
        return commandController.getPrefix(platform);
    }

    @RequiredArgsConstructor
    private class Execution {

        private final @NonNull UUID botId;

        private ImmutableSet<CommandDeclaration> allCommands;
        private Set<String> activeExtensions;
        private ImmutableSet<CommandDeclaration> filteredCommands;

        public ImmutableSet<CommandDeclaration> getAllActiveCommands() {
            this.getAllCommandsFromRegistry();
            this.getAllActiveExtensions();
            this.filterActiveCommands();
            return filteredCommands;
        }

        private void getAllCommandsFromRegistry() {
            allCommands = commandRegistry.getAllCommands();
        }

        private void getAllActiveExtensions() {
            activeExtensions = allCommands.stream()
                                          .map(CommandDeclaration::getExtensionName)
                                          .distinct()
                                          .filter(this::isExtensionActive)
                                          .collect(Collectors.toSet());
        }

        private boolean isExtensionActive(String extensionName) {
            return extensionService.isExtensionEnabled(botId, extensionName);
        }


        private void filterActiveCommands() {
            filteredCommands = allCommands.stream().filter(this::isEnabled).collect(ImmutableSet.toImmutableSet());
        }

        private boolean isEnabled(@NonNull CommandDeclaration commandDeclaration) {
            return activeExtensions.contains(commandDeclaration.getExtensionName());
        }


    }

}
