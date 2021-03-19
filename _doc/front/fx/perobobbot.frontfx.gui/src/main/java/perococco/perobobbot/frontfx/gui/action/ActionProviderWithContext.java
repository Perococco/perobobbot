package perococco.perobobbot.frontfx.gui.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import perobobbot.action.ActionProvider;

import java.util.Optional;

@RequiredArgsConstructor
public class ActionProviderWithContext implements ActionProvider {

    @NonNull
    private final ApplicationContext applicationContext;

    @Override
    public @NonNull <A> Optional<? extends A> findAction(@NonNull Class<A> actionType) {
        try {
            return Optional.of(applicationContext.getBean(actionType));
        } catch (BeansException e) {
            return Optional.empty();
        }
    }
}
