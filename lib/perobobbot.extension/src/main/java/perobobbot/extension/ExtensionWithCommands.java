package perobobbot.extension;

import lombok.NonNull;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.command.CommandRegistry;

public class ExtensionWithCommands extends ExtensionBase {

    private final @NonNull Extension extension;
    private final @NonNull CommandRegistry commandRegistry;
    private final @NonNull CommandBundle commandBundle;

    public ExtensionWithCommands(@NonNull Extension extension, @NonNull CommandRegistry commandRegistry, @NonNull CommandBundle commandBundle) {
        super(extension.getName());
        this.extension = extension;
        this.commandRegistry = commandRegistry;
        this.commandBundle = commandBundle;
    }

    @Override
    protected void onEnabled() {
        super.onEnabled();
        extension.enable();
        commandBundle.attachTo(commandRegistry);
    }

    @Override
    protected void onDisabled() {
        super.onDisabled();
        commandBundle.detach();
        extension.disable();
    }
}
