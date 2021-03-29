package perobobbot.plugin;

import com.google.common.collect.ImmutableList;
import jplugman.annotation.ExtensionPoint;
import lombok.NonNull;
import perobobbot.command.CommandDefinition;

@ExtensionPoint(version = 1)
public interface ExtensionPlugin extends PerobobbotPlugin {

    @NonNull Extension getExtension();

    @NonNull ImmutableList<CommandDefinition> getCommandDefinitions();

    @Override
    default @NonNull String getName() {
        return "Extension : '"+getExtension().getName()+"'";
    }

    @Override
    default <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }

    default void disableExtension() {
        getExtension().disable();
    }

    default @NonNull String getExtensionName() {
        return getExtension().getName();
    }
}
