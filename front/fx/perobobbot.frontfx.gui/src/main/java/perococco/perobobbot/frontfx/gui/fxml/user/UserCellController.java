package perococco.perobobbot.frontfx.gui.fxml.user;

import javafx.scene.control.Label;
import perobobbot.fx.CellController;
import perobobbot.lang.Bot;
import perobobbot.security.com.SimpleUser;

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
            roles.setText(item.getRolesAsString());
        }
    }
}
