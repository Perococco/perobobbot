package perobobbot.services;

import lombok.NonNull;
import perococco.perobobbot.services.AllOf;
import perococco.perobobbot.services.AnyOf;
import perococco.perobobbot.services.AtLeastOneOf;

public interface RequirementVisitor<T> {

    @NonNull T visit(@NonNull AnyOf anyOf);

    @NonNull T visit(@NonNull AtLeastOneOf atLeastOneOf);

    @NonNull T visit(@NonNull AllOf allOf);
}
