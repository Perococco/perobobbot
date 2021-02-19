package perococco.perobobbot.frontfx.gui.fxml.bot;

import javafx.scene.control.Label;
import perobobbot.fx.CellController;
import perobobbot.lang.Bot;

public class BotCellController implements CellController<Bot> {

    public Label name;
    public Label owner;

    @Override
    public void setSelected(boolean selected) {}

    @Override
    public void updateItem(Bot item, boolean empty) {
        if (item == null || empty) {
            name.setText("");
            owner.setText("");
        } else {
            owner.setText(item.getOwnerLogin());
            name.setText(item.getName());
        }
    }
}
