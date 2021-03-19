package perococco.perobobbot.frontfx.gui;

import lombok.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.view.SlotMapper;
import perobobbot.frontfx.model.view.SlotMapperFactory;
import perobobbot.frontfx.model.view.ViewSlot;

import java.util.Map;

@Component
public class PeroSlotMapperFactory implements SlotMapperFactory {

    @NonNull
    private final SlotMapperFactory delegate;

    public PeroSlotMapperFactory(@NonNull ApplicationArguments arguments) {
        if (arguments.containsOption("debug-fx")) {
            delegate = SlotMapperFactory.createForDebug();
        } else {
            delegate = SlotMapperFactory.createDefault();
        }
    }

    @Override
    public @NonNull SlotMapper create(@NonNull Map<String, ViewSlot> mapping) {
        return delegate.create(mapping);
    }
}
