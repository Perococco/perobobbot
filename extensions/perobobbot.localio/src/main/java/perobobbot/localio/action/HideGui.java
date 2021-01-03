package perobobbot.localio.action;

import lombok.NonNull;

public class HideGui extends LocalActionBase {

    private final GuiContext guiContext;

    public HideGui(@NonNull GuiContext guiContext) {
        super("hide-gui", "Hide the GUI");
        this.guiContext = guiContext;
    }

    @Override
    public void execute(@NonNull String[] parameters) {
        guiContext.hideGui();
    }
}
