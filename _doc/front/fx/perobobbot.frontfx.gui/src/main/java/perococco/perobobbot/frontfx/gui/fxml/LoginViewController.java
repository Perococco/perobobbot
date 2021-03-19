package perococco.perobobbot.frontfx.gui.fxml;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.ActionBinding;
import perobobbot.action.ActionManager;
import perobobbot.frontfx.action.list.SignIn;
import perobobbot.frontfx.action.list.SignParameter;
import perobobbot.frontfx.action.list.SignUp;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.fx.FXProperties;
import perobobbot.validation.Validation;
import perobobbot.validation.ValidationResult;

import java.util.Optional;
import java.util.stream.Stream;

@FXMLController
@RequiredArgsConstructor
public class LoginViewController  {
    public TextField login;
    public PasswordField password;
    public Button signInButton;
    public Button signUpButton;
    public Label error;


    private final @NonNull FXApplicationIdentity applicationIdentity;

    private final ObjectProperty<ValidationResult> validationResult = new SimpleObjectProperty<>(null);
    private final ObjectProperty<SignParameter> signParameter = new SimpleObjectProperty<>(null);

    private final @NonNull ActionManager actionManager;
    public HBox root;

    public void initialize() {
        Stream.of(this.login, this.password).forEach(t -> {
            t.textProperty().addListener(l -> updateSignParameter());
            t.setOnKeyPressed(this::onKeyPressed);
        });

        this.signInButton.disableProperty().bind(applicationIdentity.disabledProperty(SignIn.class).or(signParameter.isNull()));
        this.signUpButton.disableProperty().bind(applicationIdentity.disabledProperty(SignUp.class).or(signParameter.isNull()));

    }

    private void onKeyPressed(KeyEvent e) {
        if (e.getEventType() == KeyEvent.KEY_PRESSED && e.getCode() == KeyCode.ENTER) {
            signIn();
        }
        this.validationResult.set(null);
    }

    private @NonNull Optional<SignParameter> getSignParameter() {
        return Optional.ofNullable(signParameter.get());
    }

    private void updateSignParameter() {
        final var parameter = new SignParameter(login.getText(), password.getText());
        final var result = parameter.validate(Validation.create()).getResult();
        if (result.isValid()) {
            this.validationResult.set(null);
            this.signParameter.set(parameter);
        } else {
            this.validationResult.set(result);
            this.signParameter.set(null);
        }

    }

    public void signIn() {
        getSignParameter().ifPresent(p -> actionManager.pushAction(SignIn.class, p));
    }

    public void signUp() {
        getSignParameter().ifPresent(p -> actionManager.pushAction(SignUp.class, p));
    }
}
