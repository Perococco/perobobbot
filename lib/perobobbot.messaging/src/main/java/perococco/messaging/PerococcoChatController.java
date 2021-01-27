package perococco.messaging;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.lang.*;
import perobobbot.messaging.ChatController;

import java.util.Comparator;

@RequiredArgsConstructor
public class PerococcoChatController implements ChatController {

    private ImmutableList<MessagePreprocessor> preprocessors = ImmutableList.of();
    private ImmutableList<MessageHandler> listeners = ImmutableList.of();

    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        var processedMessage = processMessage(messageContext);
        for (MessageHandler listener : listeners) {
            listener.handleMessage(processedMessage);
        }
    }

    private @NonNull MessageContext processMessage(@NonNull MessageContext messageContext) {
        var current = messageContext;
        for (MessagePreprocessor preprocessor : preprocessors) {
            current = preprocessor.processMessage(current);
        }
        return current;
    }

    @Override
    @Synchronized
    public @NonNull Subscription addListener(@NonNull MessageHandler handler) {
        this.listeners = ListTool.addInOrderedList(this.listeners, handler, Comparator.comparingInt(MessageHandler::priority).reversed());
        return () -> removeListener(handler);
    }

    @Override
    @Synchronized
    public @NonNull Subscription addPreprocessor(@NonNull MessagePreprocessor preprocessor) {
        this.preprocessors = ListTool.addInOrderedList(this.preprocessors,preprocessor,Comparator.comparing(MessagePreprocessor::priority).reversed());
        return () -> removePreprocessor(preprocessor);
    }

    @Synchronized
    private void removeListener(MessageHandler listener) {
        this.listeners = ListTool.removeFirst(this.listeners, listener);
    }

    @Synchronized
    private void removePreprocessor(MessagePreprocessor preprocessor) {
        this.preprocessors = ListTool.removeFirst(this.preprocessors,preprocessor);
    }
}
