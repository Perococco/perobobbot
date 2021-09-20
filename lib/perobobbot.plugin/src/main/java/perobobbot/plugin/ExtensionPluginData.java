package perobobbot.plugin;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.command.CommandDeclaration;

public record ExtensionPluginData(@NonNull Extension extension,
                                  @NonNull ImmutableList<CommandDeclaration> commandDeclarations) implements PerobobbotPluginData {


    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public void disableExtension() {
        extension.disable();
    }

    public void enableExtension() {
        extension.enable();
    }

    public @NonNull
    String getExtensionName() {
        return extension.getName();
    }
}
