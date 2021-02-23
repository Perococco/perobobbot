package perobobbot.greeter;

import lombok.NonNull;
import perobobbot.extension.ExtensionWithoutCommandFactory;
import perobobbot.plugin.Extension;

public class GreeterExtensionFactory extends ExtensionWithoutCommandFactory {

    public static final String NAME = "greeter";

    public GreeterExtensionFactory() {
        super(NAME);
    }

    @Override
    protected @NonNull Extension createExtension(@NonNull Parameters parameters) {
        return new GreeterExtension(parameters.getIo(), parameters.getChatController());
    }

}
