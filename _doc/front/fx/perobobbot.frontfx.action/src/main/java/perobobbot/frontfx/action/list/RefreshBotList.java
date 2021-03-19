package perobobbot.frontfx.action.list;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;
import perobobbot.frontfx.model.ApplicationIdentity;
import perobobbot.frontfx.model.state.mutation.SetBotList;
import perobobbot.rest.client.ClientManager;

@Component
@RequiredArgsConstructor
public class RefreshBotList extends ActionOnNilNoResult {

    private final @NonNull ApplicationIdentity applicationIdentity;
    private final @NonNull ClientManager clientManager;
    private final @NonNull ErrorHandler errorHandler;

    @Override
    protected void execute() throws Throwable {
        clientManager.botClient()
                     .listBots()
                     .whenComplete((botList, error) -> {
                         if (error != null) {
                             errorHandler.handleError(error);
                         } else {
                             applicationIdentity.mutateDataState(SetBotList.with(botList));
                         }
                     });
    }
}
