package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.service.BotService;
import perobobbot.oauth.ChatTokenIdentifier;
import perobobbot.oauth.LoginTokenIdentifier;
import perobobbot.oauth.TokenIdentifier;

import java.util.Optional;

@RequiredArgsConstructor
public class LoginGetter implements TokenIdentifier.Visitor<Optional<String>> {

    private final @NonNull BotService botService;

    @Override
    public @NonNull Optional<String> visit(@NonNull ChatTokenIdentifier tokenIdentifier) {
        return botService.findBotOwnerLogin(tokenIdentifier.getBotId());
    }

    @Override
    public @NonNull Optional<String> visit(@NonNull LoginTokenIdentifier tokenIdentifier) {
        return Optional.of(tokenIdentifier.getLogin());
    }

}