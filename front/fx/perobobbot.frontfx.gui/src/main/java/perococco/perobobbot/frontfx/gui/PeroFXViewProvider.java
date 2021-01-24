package perococco.perobobbot.frontfx.gui;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.view.EmptyFXView;
import perobobbot.frontfx.model.view.FXView;
import perobobbot.frontfx.model.view.FXViewProvider;

import java.util.Optional;

@Component
@Log4j2
@RequiredArgsConstructor
public class PeroFXViewProvider implements FXViewProvider {

    @NonNull
    private final ApplicationContext applicationContext;

    @Override
    public @NonNull <C extends FXView> Optional<FXView> findFXView(@NonNull Class<? extends C> fxViewType) {
        if (fxViewType.equals(EmptyFXView.class)) {
            return Optional.ofNullable(EmptyFXView.create());
        }
        try {
            return Optional.of(applicationContext.getBean(fxViewType));
        } catch (BeansException e) {
            LOG.error("Could not find any FXView of type "+fxViewType,e);
            return Optional.empty();
        }
    }
}
