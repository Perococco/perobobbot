package perobobbot.frontfx.model.view;

import lombok.NonNull;
import perococco.perobobbot.frontfx.model.view.DebugFXView;

import java.util.Map;

public interface SlotMapperFactory {

    @NonNull
    SlotMapper create(@NonNull Map<String,ViewSlot> mapping);

    static SlotMapperFactory createDefault() {
        return m -> new SlotMapper(m, (fxView, node) -> node);
    }

    static SlotMapperFactory createForDebug() {
        return m -> new SlotMapper(m, new DebugFXView());
    }


}
