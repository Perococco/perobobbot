package perobobbot.localio.action;

import lombok.NonNull;

public class ShowGui extends LocalActionBase {

    private final GuiContext guiContext;

    public ShowGui(@NonNull GuiContext guiContext) {
        super("show-gui", "Show the GUI");
        this.guiContext = guiContext;
    }

    @Override
    public void execute(@NonNull String[] parameters) {
        guiContext.showGui();
    }
}
