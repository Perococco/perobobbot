package perobobbot.play;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.MessageContext;
import perobobbot.lang.MessagePreprocessor;

@RequiredArgsConstructor
public class PlayPreprocessor implements MessagePreprocessor {

    private final @NonNull PlayExtension playExtension;

    @Override
    public MessageContext processMessage(@NonNull MessageContext messageContext) {
        if (!messageContext.getContent().startsWith("#")) {
            return messageContext;
        }
        final var soundName = messageContext.getContent().substring(1);
        if (playExtension.canPlaySound(soundName)) {
            return messageContext.forPreprocessor().content("!play " + soundName).build();
        }
        return messageContext;

    }
}
