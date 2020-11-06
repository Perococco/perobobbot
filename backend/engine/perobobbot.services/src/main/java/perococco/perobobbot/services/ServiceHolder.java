package perococco.perobobbot.services;

import lombok.NonNull;
import lombok.Value;

@Value
public class ServiceHolder<T> {

    @NonNull T service;

    int priority;
}
