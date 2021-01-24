package perobobbot.fx.dialog;

import com.google.common.collect.ImmutableMap;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import perobobbot.validation.ValidationResult;

import java.util.Optional;

@Builder(builderClassName = "Builder")
public class DialogInfo<O> {

    @NonNull
    @Getter
    private final DialogController<?,O> dialogController;

    private final Button cancelButton;

    private final Button validateButton;

    private final Button applyButton;

    @Singular
    private final ImmutableMap<String, ControlInfo> validatableFields;

    @NonNull
    public Optional<ControlInfo> getControl(@NonNull String fieldName) {
        return Optional.ofNullable(validatableFields.get(fieldName));
    }

    @NonNull
    public Optional<Button> getCancelButton() {
        return Optional.ofNullable(cancelButton);
    }

    @NonNull
    public Optional<Button> getValidateButton() {
        return Optional.ofNullable(validateButton);
    }

    @NonNull
    public Optional<Button> getApplyButton() {
        return Optional.ofNullable(applyButton);
    }

    @NonNull
    public ObservableValue<DialogState<O>> resultProperty() {
        return dialogController.dialogStateProperty();
    }

    public void updateDecoration(@NonNull ValidationResult validationResult) {
        for (String fieldName : validationResult.getValidatedFields()) {
            getControl(fieldName)
                      .ifPresent(control -> control.updateDecoration(validationResult.getErrors(fieldName)));
        }
    }

}
