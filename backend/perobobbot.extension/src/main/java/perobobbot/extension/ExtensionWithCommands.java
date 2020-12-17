package perobobbot.extension;

import lombok.NonNull;
import perobobbot.command.CommandBundleLifeCycle;

public class ExtensionWithCommands extends ExtensionBase {

    private final @NonNull Extension extension;
    private final @NonNull CommandBundleLifeCycle commandBundleLifeCycle;

    public ExtensionWithCommands(@NonNull Extension extension, @NonNull CommandBundleLifeCycle commandBundleLifeCycle) {
        super(extension.getName());
        this.extension = extension;
        this.commandBundleLifeCycle = commandBundleLifeCycle;
    }

    @Override
    protected void onEnabled() {
        super.onEnabled();
        extension.enable();
        commandBundleLifeCycle.attachCommandBundle();
    }

    @Override
    protected void onDisabled() {
        super.onDisabled();
        commandBundleLifeCycle.detachCommandBundle();
        extension.disable();
    }
}
