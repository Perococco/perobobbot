package perococco.perobobbot.frontfx.gui.fxml;

import javafx.scene.layout.BorderPane;
import lombok.NonNull;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.frontfx.model.view.DynamicController;
import perobobbot.frontfx.model.view.FXViewProvider;
import perobobbot.frontfx.model.view.SlotMapperFactory;
import perobobbot.frontfx.model.view.SlotRegistry;
import perobobbot.lang.fp.Consumer1;

@FXMLController
public class MainWindowController extends DynamicController {

    public static final String MAIN_SLOT = "main";

    @NonNull
    private final FXApplicationIdentity fxApplicationIdentity;

    public BorderPane root;

    public MainWindowController(@NonNull SlotMapperFactory slotMapperFactory,
                                @NonNull FXViewProvider fxViewProvider,
                                @NonNull FXApplicationIdentity fxApplicationIdentity) {
        super(slotMapperFactory,fxViewProvider);
        this.fxApplicationIdentity = fxApplicationIdentity;
    }

    private final Consumer1<ApplicationStateTool> listener = this::onApplicationStateChange;

    @Override
    protected void performControllerInitialization() {
       fxApplicationIdentity.addWeakListener(listener);
       listener.accept(fxApplicationIdentity.getState());
    }

    private void onApplicationStateChange(@NonNull ApplicationStateTool tool) {
        setSlotView(MAIN_SLOT,tool.getMainFXView());
    }

    @Override
    protected void initializeSlots(@NonNull SlotRegistry slotRegistry) {
        slotRegistry.register(MAIN_SLOT,root::setCenter);
    }

}
