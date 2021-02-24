package perobobbot.plugin;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.PerobobbotException;

public class UnknownService extends PerobobbotException {

    @Getter
    private final @NonNull Class<?> serviceType;

    public UnknownService(@NonNull Class<?> serviceType) {
        super("No service with the type '"+serviceType+"' exists.");
        this.serviceType = serviceType;
    }
}
