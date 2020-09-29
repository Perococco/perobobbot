package perobobbot.service.core;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.PerobobbotException;

public class UnknownService extends PerobobbotException {

    @NonNull
    @Getter
    private final Class<?> serviceType;

    public <T> UnknownService(@NonNull Class<T> serviceType) {
        super("Service '"+serviceType.getName()+"' could not be found");
        this.serviceType = serviceType;
    }
}
