package perococco.perobobbot.frontfx.gui.fxml;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.ActionManager;
import perobobbot.action.Launchable;
import perobobbot.frontfx.action.list.SignIn;
import perobobbot.frontfx.action.list.SignParameter;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.lang.Todo;

import java.util.Optional;

@FXMLController
@RequiredArgsConstructor
public class LoginViewController {
    public TextField login;
    public TextField password;
    public Button signIn;
    public Button signUp;

    private final @NonNull ActionManager actionManager;

    private final @NonNull FXApplicationIdentity applicationIdentity;

    public void initialize() {
        actionManager.binder(Launchable.single(SignIn.class)).createBinding(signIn,this::formSignInParameter);
    }

    private @NonNull Optional<SignParameter> formSignInParameter() {
        return Todo.TODO();
    }

}
