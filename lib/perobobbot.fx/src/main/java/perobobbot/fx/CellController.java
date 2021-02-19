package perobobbot.fx;

public interface CellController<T> {

    void setSelected(boolean selected);
    void updateItem(T item, boolean empty);
}
