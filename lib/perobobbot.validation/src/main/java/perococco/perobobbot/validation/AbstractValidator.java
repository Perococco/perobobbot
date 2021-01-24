package perococco.perobobbot.validation;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;
import perobobbot.lang.fp.Predicate1;
import perobobbot.validation.ErrorType;
import perobobbot.validation.PathValidator;
import perobobbot.validation.Validator;
import perobobbot.validation.ValidatorFactory;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractValidator<O,V extends Validator<O,V>> implements Validator<O,V> {

    @NonNull
    private final ValidationContext context;

    private final String fieldName;

    protected final O value;

    public AbstractValidator(@NonNull ValidationContext context,
                             @NonNull String fieldName, O value) {
        this.context = context;
        this.fieldName = fieldName;
        this.value = value;
        context.addValidatedField(fieldName);
    }

    @Override
    public @NonNull V isNotNull() {
        if (value == null) {
            addError(ErrorType.NOT_NULL_REQUIRED);
        }
        return getThis();
    }

    protected abstract V getThis();

    @Override
    public V errorIf(@NonNull Predicate1<O> test, @NonNull String errorType) {
        if (value != null && test.test(value)) {
            addError(errorType);
        }
        return getThis();
    }

    @Override
    public V errorIfNot(@NonNull Predicate1<O> test, @NonNull String errorType) {
        if (value != null && !test.test(value)) {
            addError(errorType);
        }
        return getThis();
    }

    @NonNull
    public V addError(@NonNull String errorType) {
        context.addError(fieldName,errorType);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<O> getValidValue() {
        if (isValid()) {
            return Optional.ofNullable(value);
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull O getValue() {
        if (isValid() && value != null) {
            return value;
        } else {
            throw new IllegalStateException("Invalid value");
        }
    }

    @Override
    public boolean isValid() {
        return context.isFieldValid(fieldName);
    }

    @Override
    public <T, V extends Validator<T, V>> @NonNull V map(@NonNull Function1<? super O, ? extends T> mapper,
                                                           @NonNull ValidatorFactory<? super T, ? extends V> factory) {
        final Function<? super T, ? extends V> f = t -> factory.create(context,fieldName,t);

        if (value == null) {
            return f.apply(null);
        } else {
            try {
                final T converted = mapper.f(value);
                return f.apply(converted);
            } catch (Throwable t) {
                return f.apply(null).addError(ErrorType.INVALID_VALUE);
            }
        }
    }

    @Override
    public @NonNull <T> Validator<T, ?> map(@NonNull Function1<? super O, ? extends T> mapper) {
        return map(mapper, new ValidatorFactory<T, PeroValidator<T>>() {
            @NonNull
            @Override
            public PeroValidator<T> create(@NonNull ValidationContext context, @NonNull String fieldName, T value) {
                return new PeroValidator<>(context, fieldName, value);
            }
        });
    }

    @Override
    public @NonNull PathValidator toPathValidator(Function1<? super O, ? extends Path> pathBuilder) {
        return map(pathBuilder, PeroPathValidator::new);
    }

}
