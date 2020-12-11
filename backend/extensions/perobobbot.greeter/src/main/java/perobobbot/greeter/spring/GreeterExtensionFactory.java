package perobobbot.greeter.spring;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.extension.Extension;
import perobobbot.extension.ExtensionWithoutCommandFactory;
import perobobbot.greeter.GreeterExtension;
import perobobbot.lang.Packages;

@Component
public class GreeterExtensionFactory extends ExtensionWithoutCommandFactory {

    public static @NonNull Packages provider() {
        return Packages.with("[Extension] Greeter",GreeterExtensionFactory.class);
    }

    public static final String NAME = "greeter";

    public GreeterExtensionFactory(@NonNull Parameters parameters) {
        super(NAME, parameters);
    }

    @Override
    protected @NonNull Extension createExtension(@NonNull String userId, @NonNull Parameters parameters) {
        return new GreeterExtension(userId, parameters.getIo(), parameters.getChatController());
    }

}
