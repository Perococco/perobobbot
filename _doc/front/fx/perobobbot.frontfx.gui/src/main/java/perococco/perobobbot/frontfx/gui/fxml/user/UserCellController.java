package perococco.perobobbot.frontfx.gui.fxml.user;

import javafx.scene.control.Label;
import lombok.NonNull;
import perobobbot.fx.CellController;
import perobobbot.lang.Bot;
import perobobbot.security.com.SimpleUser;

import java.util.stream.Collectors;

public class UserCellController implements CellController<SimpleUser> {

    public Label login;
    public Label roles;

    @Override
    public void setSelected(boolean selected) {}

    @Override
    public void updateItem(SimpleUser item, boolean empty) {
        if (item == null || empty) {
            login.setText("");
            roles.setText("");
        } else {
            login.setText(item.getLogin());
            roles.setText(item.getRoles().stream().map(Enum::name).collect(Collectors.joining(",")));
        }
    }
}
