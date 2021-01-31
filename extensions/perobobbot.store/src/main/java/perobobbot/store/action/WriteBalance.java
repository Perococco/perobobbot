package perobobbot.store.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParsing;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.PointType;
import perobobbot.store.StoreExtension;

@RequiredArgsConstructor
public class WriteBalance implements CommandAction {

    public static final String USERID_PARAMETER = "userId";
    public static final String AMOUNT_PARAMETER = "amount";

    private final @NonNull StoreExtension extension;

    @Override
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context) {
        final var userId = parsing.getParameter(USERID_PARAMETER);
        final var amount = parsing.getIntParameter(AMOUNT_PARAMETER);
        extension.addSomePoint(userId, context.getPlatform(), context.getChannelName(), PointType.CREDIT, amount);
    }
}
