package perococco.perobobbot.program.greeter;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.MessageContext;
import perobobbot.common.lang.MessageHandler;

import java.util.Arrays;

@RequiredArgsConstructor
public class HelloMessageHandler implements MessageHandler {

    private static final ImmutableSet<String> FRENCH_WORDS = ImmutableSet.of(
            "salut","hello","bonjour","bonsoir","coucou","slt"
    );

    @NonNull
    private final HelloIdentity hello;

    @Override
    public boolean handleMessage(@NonNull MessageContext messageContext) {
        if (hello.getRootState().hasBeenGreeted(UserOnChannel.from(messageContext))) {
            return false;
        }
        if (containsHello(messageContext.getContent())) {
            hello.greetUser(messageContext.getChannelInfo(), messageContext.getMessageOwner());
        }
        return false;
    }

    private boolean containsHello(@NonNull String message) {
        final String prepared = prepareMessage(message);
        return Arrays.stream(prepared.split("\\s"))
                     .anyMatch(this::isHelloWord);
    }

    @NonNull
    private String prepareMessage(String message) {
        return message.trim().toLowerCase();
    }

    private boolean isHelloWord(@NonNull String word) {
        return FRENCH_WORDS.contains(word);
    }
}
