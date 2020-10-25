package perobobbot.service.core;

import lombok.NonNull;
import perococco.perobobbot.service.core.AllOf;
import perococco.perobobbot.service.core.AnyOf;
import perococco.perobobbot.service.core.AtLeastOneOf;

public interface RequirementVisitor<T> {

    @NonNull T visit(@NonNull AnyOf anyOf);

    @NonNull T visit(@NonNull AtLeastOneOf atLeastOneOf);

    @NonNull T visit(@NonNull AllOf allOf);
}
