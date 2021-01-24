package perococco.perobobbot.validation;


import lombok.NonNull;

public class PeroValidator<O> extends AbstractValidator<O, PeroValidator<O>> {

    public PeroValidator(@NonNull ValidationContext context, @NonNull String fieldName, O value) {
        super(context, fieldName, value);
    }

    @Override
    protected PeroValidator<O> getThis() {
        return this;
    }
}
