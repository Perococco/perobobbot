package perobobbot.frontfx.model.view;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

/**
 * A dynamic controller is a controller that provides slot
 * that can by "dynamically" filled with {@link FXView}
 */
@Log4j2
@RequiredArgsConstructor
public abstract class DynamicController{

    private Map<String, ViewSlot> slots = new HashMap<>();

    @NonNull
    private final SlotMapperFactory slotMapperFactory;

    @NonNull
    private final FXViewProvider fxViewProvider;

    /**
     * used by FXMLLoader.
     */
    @SuppressWarnings("unused")
    public void initialize() {
        this.initializeSlots(slotMapperFactory.create(slots));
        this.performControllerInitialization();
    }

    protected abstract void initializeSlots(@NonNull SlotRegistry slotRegistry);
    protected abstract void performControllerInitialization();



    protected boolean setSlotViewEmpty(@NonNull String slotName) {
        return setSlotView(slotName,EmptyFXView.class);
    }

    protected boolean setSlotView(@NonNull String slotName, @NonNull Class<? extends FXView> fxViewType) {
        final ViewSlot slot = slots.get(slotName);
        if (slot == null) {
            LOG.warn("Could not find slot with name {} in controller {}", slotName, getClass());
            return false;
        }

        final FXView fxView = fxViewProvider.getFXView(fxViewType);
        slot.setFxView(fxView);
        return true;
    }

}
