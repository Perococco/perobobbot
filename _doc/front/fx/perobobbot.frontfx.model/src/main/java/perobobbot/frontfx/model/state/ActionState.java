package perobobbot.frontfx.model.state;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.Action;
import perobobbot.state.SetState;

import java.util.Collection;

/**
 * Contains the state of each action : is an action disabled or enabled
 */
@RequiredArgsConstructor
@Getter
public class ActionState {


    @NonNull
    public static ActionState allEnabled() {
        return new ActionState(SetState.empty());
    }


    @NonNull
    private final SetState<Class<? extends Action<?,?>>> disabledActions;


    public boolean isEnabled(@NonNull Class<? extends Action<?,?>> action) {
        return !isDisabled(action);
    }

    public boolean isDisabled(@NonNull Class<? extends Action<?, ?>> action) {
        return disabledActions.contains(action);
    }


    public Builder toBuilder() {
        return new Builder(disabledActions.toBuilder());
    }


    @RequiredArgsConstructor
    public static class Builder {

        @NonNull
        private final SetState.Builder<Class<? extends Action<?,?>>> disableActionBuilder;

        @NonNull
        public Builder enableAction(@NonNull Class<? extends Action<?,?>> action) {
            disableActionBuilder.remove(action);
            return this;
        }

        @NonNull
        public Builder enableActions(@NonNull Collection<Class<? extends Action<?,?>>> action) {
            action.forEach(disableActionBuilder::remove);
            return this;
        }

        @NonNull
        public Builder disableAction(@NonNull Class<? extends Action<?,?>> action) {
            disableActionBuilder.add(action);
            return this;
        }

        @NonNull
        public Builder disableActions(@NonNull Collection<Class<? extends Action<?,?>>> action) {
            action.forEach(disableActionBuilder::add);
            return this;
        }

    }



}
