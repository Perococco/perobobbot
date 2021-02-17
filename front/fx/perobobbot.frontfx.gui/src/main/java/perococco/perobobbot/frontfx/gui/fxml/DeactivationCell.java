package perococco.perobobbot.frontfx.gui.fxml;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import lombok.NonNull;
import perobobbot.lang.Todo;
import perobobbot.security.com.SimpleUser;

public class DeactivationCell extends TableCell<SimpleUser,SimpleUser> {

    private final @NonNull CheckBox checkBox = new CheckBox();

    public DeactivationCell() {
        checkBox.setOnAction(e -> updateUser());
    }

    @Override
    protected void updateItem(SimpleUser item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            this.setGraphic(null);
        } else {
            this.setGraphic(this.checkBox);
            this.checkBox.setSelected(item.isDeactivated());

        }
    }

    private void updateUser() {
        final var user = getItem();
        if (user == null) {
            return;
        }
        final var deactivated = this.checkBox.isSelected();

        Todo.TODO("Update user: "+user.getLogin()+" -> "+deactivated);
    }


}
