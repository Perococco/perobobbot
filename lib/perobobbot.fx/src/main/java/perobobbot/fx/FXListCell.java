package perobobbot.fx;

import javafx.scene.Node;
import javafx.scene.control.ListCell;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FXListCell<T> extends ListCell<T> {

    public static <T> @NonNull FXListCell<T> create(@NonNull FXLoaderFactory loaderFactory, Class<? extends CellController<T>> controllerClass) {
        return new FXListCell<>(loaderFactory.create(controllerClass));
    }

    private final @NonNull FXLoader loader;

    private CellController<T> controller;
    private Node node;


    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            this.setText(null);
            this.setGraphic(null);
        } else {
            loadIfNecessary();
            this.setText(null);
            this.setGraphic(node);
        }
        if (controller != null) {
            controller.updateItem(item, empty);
        }
    }

    private void loadIfNecessary() {
        if (controller != null) {
            return;
        }
        final var result = loader.load();
        this.controller = result.getController();
        this.node = result.getRoot();
    }
}
