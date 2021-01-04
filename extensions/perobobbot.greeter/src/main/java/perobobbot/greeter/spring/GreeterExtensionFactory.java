package perobobbot.greeter.spring;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.extension.Extension;
import perobobbot.extension.ExtensionWithoutCommandFactory;
import perobobbot.greeter.GreeterExtension;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;

@Component
public class GreeterExtensionFactory extends ExtensionWithoutCommandFactory {

    public static @NonNull Plugin provider() {
        return Plugin.with(PluginType.EXTENSION,"Greeter", GreeterExtensionFactory.class);
    }

    public static final String NAME = "greeter";

    public GreeterExtensionFactory(@NonNull Parameters parameters) {
        super(NAME, parameters);
    }

    @Override
    protected @NonNull Extension createExtension(@NonNull Parameters parameters) {
        return new GreeterExtension(parameters.getIo(), parameters.getChatController());
    }

}
