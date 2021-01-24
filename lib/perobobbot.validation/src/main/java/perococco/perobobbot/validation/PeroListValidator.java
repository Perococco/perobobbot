package perococco.perobobbot.validation;

import lombok.NonNull;
import perobobbot.validation.ErrorType;
import perobobbot.validation.ListValidator;

import java.util.List;


public class PeroListValidator<T> extends AbstractValidator<List<T>, ListValidator<T>> implements ListValidator<T> {

    public PeroListValidator(@NonNull ValidationContext context, @NonNull String fieldName, List<T> value) {
        super(context, fieldName, value);
    }


    @Override
    protected ListValidator<T> getThis() {
        return this;
    }

    @Override
    public @NonNull ListValidator<T> isNotEmpty() {
        return errorIf(List::isEmpty, ErrorType.NOT_EMPTY_LIST_REQUIRED);
    }
}
