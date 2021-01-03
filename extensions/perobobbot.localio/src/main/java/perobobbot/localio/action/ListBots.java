package perobobbot.localio.action;

import lombok.NonNull;
import perobobbot.data.service.BotService;
import perobobbot.lang.Bot;
import perobobbot.localio.LocalAction;

import java.util.Locale;

public class ListBots extends LocalActionBase {

    private final BotService botService;

    public ListBots(@NonNull BotService botService) {
        super("list-bot", "List the bots of a user : list-bot <user-login>");
        this.botService = botService;
    }

    @Override
    public void execute(@NonNull String[] parameters) {
        if (parameters.length != 1) {
            throw new IllegalArgumentException("Missing parameters. Usage: list-bot <user-login>");
        }
        final var login = parameters[0].trim();
        final var bots = botService.listBots(login);
        if (bots.isEmpty()) {
            System.out.println("No bot for user '"+login+"'");
        } else {
            System.out.println("Bot for user '"+login+"'");
            for (Bot bot : bots) {
                System.out.format("  - '%-10s' : %s%n",bot.getName(),bot.getId());
            }
        }
    }
}
