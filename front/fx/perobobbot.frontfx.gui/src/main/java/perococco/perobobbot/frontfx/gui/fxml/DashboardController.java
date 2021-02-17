package perococco.perobobbot.frontfx.gui.fxml;

import javafx.scene.layout.BorderPane;
import lombok.NonNull;
import perobobbot.action.ActionExecutor;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.frontfx.model.view.*;
import perobobbot.lang.SubscriptionHolder;
import perococco.perobobbot.frontfx.gui.view.DashboardMenuView;
import perococco.perobobbot.frontfx.gui.view.StatusBarFXView;

@FXMLController
public class DashboardController extends DynamicController implements PluggableController {

    public static final String STATUS_BAR = "statusBar";
    public static final String MAIN = "main";
    public static final String COORDINATOR = "coordinator";

    private final @NonNull FXApplicationIdentity applicationIdentity;
    private final @NonNull ActionExecutor actionExecutor;

    private final SubscriptionHolder identitySubscription = new SubscriptionHolder();

    public BorderPane root;

    public DashboardController(@NonNull SlotMapperFactory slotMapperFactory,
                               @NonNull FXViewProvider fxViewProvider,
                               @NonNull FXApplicationIdentity applicationIdentity,
                               @NonNull ActionExecutor actionExecutor) {
        super(slotMapperFactory, fxViewProvider);
        this.applicationIdentity = applicationIdentity;
        this.actionExecutor = actionExecutor;
    }

    @Override
    protected void initializeSlots(@NonNull SlotRegistry slotRegistry) {
        slotRegistry.register(STATUS_BAR, root::setTop);
        slotRegistry.register(MAIN, root::setCenter);
        slotRegistry.register(COORDINATOR, root::setLeft);
    }

    @Override
    protected void performControllerInitialization() {}

    @Override
    public void onShowing() {
        identitySubscription.replaceWith(() -> this.applicationIdentity.addListenerAndCall(this::updateViews));
    }

    @Override
    public void onHiding() {
        identitySubscription.unsubscribe();
    }


    private void updateViews(@NonNull ApplicationStateTool tool) {
        setSlotView(STATUS_BAR, StatusBarFXView.class);
        setSlotView(COORDINATOR, DashboardMenuView.class);
        setSlotView(MAIN, tool.getDashboardMainView());
    }

}
