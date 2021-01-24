package perococco.perobobbot.frontfx.action;

import javafx.scene.control.ButtonBase;
import javafx.scene.control.MenuItem;
import lombok.NonNull;
import perococco.perobobbot.frontfx.action.iteminfo.ButtonBaseBinder;
import perococco.perobobbot.frontfx.action.iteminfo.MenuItemBinder;
import perococco.perobobbot.frontfx.action.iteminfo.ObjectBinder;

public class ItemInfoProvider {

    @NonNull
    public ItemInfo createInfo(@NonNull Object item) {
        if (item instanceof ButtonBase) {
            return forButtonBase(((ButtonBase) item));
        }

        if (item instanceof MenuItem) {
            return forMenuItem((MenuItem) item);
        }

        return new ObjectBinder(item);
    }

    @NonNull
    private ItemInfo forButtonBase(@NonNull ButtonBase buttonBase) {
        return new ButtonBaseBinder(buttonBase);
    }

    @NonNull
    private ItemInfo forMenuItem(@NonNull MenuItem menuItem) {
        return new MenuItemBinder(menuItem);
    }
}
