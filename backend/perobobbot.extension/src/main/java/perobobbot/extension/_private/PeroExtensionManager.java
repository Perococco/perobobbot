package perobobbot.extension._private;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.extension.*;
import perobobbot.lang.fp.Function1;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PeroExtensionManager implements ExtensionManager {

    public static @NonNull ExtensionManager create(@NonNull String userId,
                                                   @NonNull ImmutableMap<String, ExtensionFactory> extensionFactories,
                                                   @NonNull Function1<? super ExtensionManager, ? extends CommandBundleLifeCycle> commandBundleLifeCycleFactory) {
        final ExtensionManager extensionManager = new PeroExtensionManager(userId, extensionFactories, commandBundleLifeCycleFactory);
        return extensionManager;
    }

    @Getter
    private final @NonNull String userId;

    private final @NonNull ImmutableMap<String, ExtensionFactory> extensionFactories;

    private final @NonNull Map<String, Extension> enabledExtensions = new HashMap<>();

    private final CommandBundleLifeCycle commandBundleLifeCycle;

    public PeroExtensionManager(@NonNull String userId,
                                @NonNull ImmutableMap<String, ExtensionFactory> extensionFactories,
                                @NonNull Function1<? super ExtensionManager, ? extends CommandBundleLifeCycle> commandBundleLifeCycleFactory) {
        this.userId = userId;
        this.extensionFactories = extensionFactories;
        this.commandBundleLifeCycle = commandBundleLifeCycleFactory.f(this);
        this.commandBundleLifeCycle.attachCommandBundle();
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
        final Extension extension = extensionFactory.create(userId);
        this.enabledExtensions.put(extensionFactory.getExtensionName(), extension);
        extension.enable();
    }

    private void disableExtension(@NonNull ExtensionFactory extensionFactory) {
        Optional.ofNullable(this.enabledExtensions.remove(extensionFactory.getExtensionName()))
                .ifPresent(Extension::disable);
    }

}
