package perobobbot.localio.action;

import lombok.NonNull;
import perobobbot.data.service.BotService;

public class CreateBot extends LocalActionBase {

    private final BotService botService;

    public CreateBot(@NonNull BotService botService) {
        super("create-bot", "Create a bot for a user: create-bot <user-login> <bot-name>");
        this.botService = botService;
    }

    @Override
    public void execute(@NonNull String[] parameters) {
        if (parameters.length != 2) {
            throw new IllegalArgumentException("Missing parameters. Usage: create-bot <user-login> <bot-name>");
        }
        final var bot = botService.createBot(parameters[0],parameters[1]);
        System.out.println("Bot created UUID: "+bot.getId());
    }
}
