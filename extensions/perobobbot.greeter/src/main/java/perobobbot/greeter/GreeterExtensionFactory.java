package perobobbot.greeter;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.chat.core.IO;
import perobobbot.extension.ExtensionWithoutCommandFactory;
import perobobbot.messaging.ChatController;
import perobobbot.plugin.Extension;
import perobobbot.plugin.Requirement;
import perobobbot.plugin.ServiceProvider;

public class GreeterExtensionFactory extends ExtensionWithoutCommandFactory {

    public static final String NAME = "greeter";

    public GreeterExtensionFactory() {
        super(NAME);
    }

    @Override
    public @NonNull ImmutableSet<Requirement> getRequirements() {
        return ImmutableSet.of(Requirement.required(IO.class), Requirement.required(ChatController.class));
    }

    @Override
    protected @NonNull Extension createExtension(@NonNull ServiceProvider serviceProvider) {
        return new GreeterExtension(serviceProvider.getService(IO.class), serviceProvider.getService(ChatController.class));
    }

}
