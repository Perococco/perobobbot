package perobobbot.store.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParsing;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.PointType;
import perobobbot.store.StoreExtension;

@RequiredArgsConstructor
public class ReadBalance implements CommandAction {

    private final @NonNull StoreExtension storeExtension;

    @Override
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context) {
        storeExtension.showMyPoint(context.getChatConnectionInfo(), context.getMessageOwner(), context.getChannelName(), PointType.CREDIT);
    }
}
