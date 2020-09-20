package perococco.perobobbot.service.core;

import lombok.NonNull;
import lombok.Value;

@Value
public class ServiceHolder<T> {

    @NonNull T service;

    int priority;
}
