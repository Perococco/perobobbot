package perobobbot.extension._private;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Delegate;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.command.CommandRegistry;
import perobobbot.command.ROCommandRegistry;
import perobobbot.extension.*;
import perobobbot.lang.Bot;
import perobobbot.lang.fp.Function1;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PeroExtensionManager implements ExtensionManager {

    public static @NonNull ExtensionManager create(@NonNull Bot bot,
                                                   @NonNull ImmutableMap<String, ExtensionFactory> extensionFactories,
                                                   @NonNull Function1<? super ExtensionManager, ? extends CommandBundle> commandBundle) {
        final PeroExtensionManager extensionManager = new PeroExtensionManager(bot, extensionFactories, commandBundle);

        extensionManager.enableAutoStartExtensions();

        return extensionManager;
    }

    @Getter
    private final @NonNull Bot bot;

    @Delegate(types = ROCommandRegistry.class)
    private final @NonNull CommandRegistry commandRegistry;

    private final @NonNull ImmutableMap<String, ExtensionFactory> extensionFactories;

    private final @NonNull Map<String, Extension> enabledExtensions = new HashMap<>();

    private final CommandBundle commandBundle;

    private PeroExtensionManager(@NonNull Bot bot,
                                 @NonNull ImmutableMap<String, ExtensionFactory> extensionFactories,
                                 @NonNull Function1<? super ExtensionManager, ? extends CommandBundle> commandBundleFactory) {
        this.bot = bot;
        this.extensionFactories = extensionFactories;
        this.commandBundle = commandBundleFactory.f(this);
        this.commandRegistry = CommandRegistry.create();
        this.commandBundle.attachTo(commandRegistry);
    }

    private void enableAutoStartExtensions() {
        extensionFactories.values()
                          .stream()
                          .filter(ExtensionFactory::isAutoStart)
                          .forEach(this::enableExtension);
    }


    private @NonNull ExtensionFactory getExtensionFactory(@NonNull String extensionName) {
        final var extension = extensionFactories.get(extensionName);
        if (extension == null) {
            throw new UnknownExtension(extensionName);
        }
        return extension;
    }

    @Override
    public void enableExtension(@NonNull String extensionName) {
        enableExtension(getExtensionFactory(extensionName));
    }

    @Override
    public void disableExtension(@NonNull String extensionName) {
        disableExtension(getExtensionFactory(extensionName));
    }

    @Override
    public void enableAll() {
        extensionFactories.values().forEach(this::enableExtension);
    }

    @Override
    public void disableAll() {
        extensionFactories.values().forEach(this::disableExtension);
    }

    @Override
    public @NonNull ImmutableSet<ExtensionInfo> getExtensionInfo() {
        return extensionFactories.values().stream()
                                 .map(this::retrieveExtensionInformation)
                                 .collect(ImmutableSet.toImmutableSet());
    }

    private @NonNull ExtensionInfo retrieveExtensionInformation(@NonNull ExtensionFactory factory) {
        return new ExtensionInfo(factory.getExtensionName(), enabledExtensions.containsKey(factory.getExtensionName()));
    }

    private void enableExtension(@NonNull ExtensionFactory extensionFactory) {
        if (enabledExtensions.containsKey(extensionFactory.getExtensionName())) {
            return;
        }
        final Extension extension = extensionFactory.create(bot,commandRegistry);
        this.enabledExtensions.put(extensionFactory.getExtensionName(), extension);
        extension.enable();
    }

    private void disableExtension(@NonNull ExtensionFactory extensionFactory) {
        Optional.ofNullable(this.enabledExtensions.remove(extensionFactory.getExtensionName()))
                .ifPresent(Extension::disable);
    }

}
