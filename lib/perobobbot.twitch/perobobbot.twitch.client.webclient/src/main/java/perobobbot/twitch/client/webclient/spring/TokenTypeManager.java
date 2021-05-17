package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.oauth.TokenType;
import perobobbot.twitch.client.api.TokenTypeProvider;

import java.util.Deque;
import java.util.LinkedList;

@Component
public class TokenTypeManager implements TokenTypeProvider {

    private static final ThreadLocal<Deque<TokenType>> TOKEN_TYPES = ThreadLocal.withInitial(LinkedList::new);

    public void push(@NonNull TokenType tokenType) {
        TOKEN_TYPES.get().addLast(tokenType);
    }

    public void pop() {
        final var queue = TOKEN_TYPES.get();
        if (queue.size() <=1) {
            TOKEN_TYPES.remove();
        }
        queue.removeLast();
    }

    @Override
    public @NonNull TokenType getTokenType() {
        final var queue = TOKEN_TYPES.get();
        if (queue.isEmpty()) {
            TOKEN_TYPES.remove();
            throw new IllegalStateException("Not token type queue available");
        }
        return queue.getLast();
    }

}
