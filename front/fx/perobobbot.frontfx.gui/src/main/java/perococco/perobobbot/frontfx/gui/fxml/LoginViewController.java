package perococco.perobobbot.frontfx.gui.fxml;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.ActionBinding;
import perobobbot.action.ActionManager;
import perobobbot.action.Launchable;
import perobobbot.frontfx.action.list.SignIn;
import perobobbot.frontfx.action.list.SignParameter;
import perobobbot.frontfx.action.list.SignUp;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.frontfx.model.view.PluggableController;
import perobobbot.fx.dialog.ValidatableField;
import perobobbot.validation.Validation;
import perobobbot.validation.ValidationResult;

import java.util.Optional;

@FXMLController
@RequiredArgsConstructor
public class LoginViewController implements PluggableController {
    public TextField login;
    public PasswordField password;
    public Button signIn;
    public Button signUp;
    public Label error;

    private final ObjectProperty<ValidationResult> validationResult = new SimpleObjectProperty<>(null);
    private final ObjectProperty<SignParameter> signParameter = new SimpleObjectProperty<>(null);

    private final @NonNull ActionManager actionManager;

    private ActionBinding signInBinding;
    private ActionBinding singUpBinding;

    public void initialize() {
        this.login.textProperty().addListener(l-> updateSignParameter());
        this.password.textProperty().addListener(l-> updateSignParameter());

        this.signInBinding = actionManager.binder(Launchable.single(SignIn.class)).createBinding(signIn,this::getSignParameter);
        this.singUpBinding = actionManager.binder(Launchable.single(SignUp.class)).createBinding(signUp, this::getSignParameter);

        this.signInBinding.filteredProperty().bind(signParameter.isNull());
        this.singUpBinding.filteredProperty().bind(signParameter.isNull());

    }

    @Override
    public void onShowing() {
        this.singUpBinding.bind();
        this.signInBinding.bind();
    }

    @Override
    public void onHiding() {
        this.singUpBinding.unbind();
        this.signInBinding.unbind();
    }

    private @NonNull Optional<SignParameter> getSignParameter() {
        return Optional.ofNullable(signParameter.get());
    }

    private void updateSignParameter() {
        final var parameter = new SignParameter(login.getText(),password.getText());
        final var result = parameter.validate(Validation.create()).getResult();
        if (result.isValid()) {
            this.validationResult.set(null);
            this.signParameter.set(parameter);
        } else {
            this.validationResult.set(result);
            this.signParameter.set(null);
        }

    }

}
