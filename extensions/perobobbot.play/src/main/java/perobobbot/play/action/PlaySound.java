package perobobbot.play.action;

import lombok.NonNull;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParsing;
import perobobbot.lang.ExecutionContext;
import perobobbot.play.PlayExtension;

public class PlaySound implements CommandAction {

    private final @NonNull PlayExtension extension;

    public PlaySound(PlayExtension extension) {
        this.extension = extension;
    }

    @Override
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context) {
        extension.playSound(parsing.getParameter("soundName"));
    }

}
