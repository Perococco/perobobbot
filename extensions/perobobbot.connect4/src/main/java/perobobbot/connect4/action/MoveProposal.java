package perobobbot.connect4.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParsing;
import perobobbot.connect4.Connect4Extension;
import perobobbot.lang.ExecutionContext;

@RequiredArgsConstructor
public class MoveProposal implements CommandAction  {

    private final @NonNull Connect4Extension extension;

    @Override
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context) {
        final int columnIndex = parsing.getIntParameter("column");
        extension.playAt(columnIndex);
    }
}
