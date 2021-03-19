package perobobbot.frontfx.action;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.action.Action;
import perobobbot.lang.PerobobbotException;

public class ActionCancelled extends PerobobbotException {

    @NonNull
    @Getter
    private final Class<? extends Action> actionType;

    public ActionCancelled(@NonNull Class<? extends Action> actionType) {
        super("Action has been cancelled : "+actionType);
        this.actionType = actionType;
    }
}
