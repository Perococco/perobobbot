package perococco.messaging;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.lang.ListTool;
import perobobbot.lang.MessageContext;
import perobobbot.lang.MessageHandler;
import perobobbot.lang.Subscription;
import perobobbot.messaging.ChatController;

import java.util.Comparator;

@RequiredArgsConstructor
public class PerococcoChatController implements ChatController {

    private ImmutableList<MessageHandler> listeners = ImmutableList.of();

    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        for (MessageHandler listener : listeners) {
            listener.handleMessage(messageContext);
        }
    }

    @Override
    @Synchronized
    public @NonNull Subscription addListener(@NonNull MessageHandler handler) {
        this.listeners = ListTool.addInOrderedList(this.listeners, handler, Comparator.comparingInt(MessageHandler::priority).reversed());
        return () -> removeListener(handler);
    }

    @Synchronized
    private void removeListener(MessageHandler listener) {
        this.listeners = ListTool.removeFirst(this.listeners, listener);
    }

}
