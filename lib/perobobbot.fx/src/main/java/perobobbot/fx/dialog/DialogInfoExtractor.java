package perobobbot.fx.dialog;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.i18n.Dictionary;
import perobobbot.lang.fp.Consumer1;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DialogInfoExtractor<O> {

    @NonNull
    public static <O> DialogInfo<O> extract(@NonNull Dictionary dictionary,
                                            @NonNull DialogController<?, O> controller) {
        return new DialogInfoExtractor<>(dictionary, controller, controller).extract();
    }

    @NonNull
    private final Dictionary dictionary;

    @NonNull
    private final DialogController<?, O> dialogController;

    @NonNull
    private final DialogResultHandler<O> resultHandler;

    @NonNull
    private final DialogInfo.Builder<O> builder = DialogInfo.<O>builder();

    @NonNull
    private DialogInfo<O> extract() {
        final Field[] fields = dialogController.getClass().getDeclaredFields();
        Arrays.stream(fields)
              .filter(f -> Node.class.isAssignableFrom(f.getType()))
              .forEach(this::parseField);

        return builder.dialogController(dialogController)
                      .build();
    }

    private void parseField(Field f) {
        this.checkButton(f, CancelButton.class, builder::cancelButton);
        this.checkButton(f, ApplyButton.class, builder::applyButton);
        this.checkButton(f, ValidateButton.class, builder::validateButton);

        this.checkValidatable(f);
    }

    private void checkValidatable(@NonNull Field f) {
        final Optional<String> name = getValidatableFieldName(f);
        name.ifPresent(n -> getControlInfoFromField(f).ifPresent(c -> builder.validatableField(n, c)));
    }

    @NonNull
    private Optional<String> getValidatableFieldName(@NonNull Field field) {
        final ValidatableField annotation = field.getAnnotation(ValidatableField.class);
        if (annotation == null) {
            return Optional.empty();
        }
        final String name = annotation.value();
        if (name.equals(ValidatableField.NO_VALUE)) {
            return Optional.of(field.getName());
        }
        return Optional.of(name);
    }


    private void checkButton(@NonNull Field field, @NonNull Class<? extends Annotation> annotation,
                             @NonNull Consumer1<Button> consumer) {
        if (field.isAnnotationPresent(annotation)) {
            getButtonFromField(field).ifPresent(consumer);
        }
    }

    @NonNull
    private Optional<Button> getButtonFromField(@NonNull Field f) {
        return getValueFromField(f, Button.class);
    }

    @NonNull
    private Optional<ControlInfo> getControlInfoFromField(@NonNull Field f) {
        return getValueFromField(f, Control.class)
                .map(c -> new ControlInfo(dictionary, c));
    }

    @NonNull
    private <T> Optional<T> getValueFromField(@NonNull Field f, @NonNull Class<T> expectedType) {
        if (expectedType.isAssignableFrom(f.getType())) {
            boolean accessible = f.canAccess(dialogController);
            try {
                if (!accessible) {
                    f.setAccessible(true);
                }
                return Optional.of(expectedType.cast(f.get(dialogController)));
            } catch (Exception e) {
                return Optional.empty();
            } finally {
                if (!accessible) {
                    f.setAccessible(false);
                }
            }
        }
        return Optional.empty();
    }

}
