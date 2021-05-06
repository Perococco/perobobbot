package perococco.perobobbot.chat.advanced;

import lombok.*;
import perobobbot.chat.advanced.*;
import perobobbot.chat.advanced.event.AdvancedChatEvent;
import perobobbot.chat.advanced.event.ReceivedMessage;
import perobobbot.chat.core.Chat;
import perobobbot.chat.core.event.Error;
import perobobbot.chat.core.event.*;
import perobobbot.lang.Listeners;
import perobobbot.lang.Instants;
import perobobbot.lang.Subscription;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class PerococcoAdvancedChat<M> implements AdvancedChat<M> {

    @NonNull
    private final Chat chat;

    @NonNull
    private final RequestAnswerMatcher<M> matcher;

    private final MessageConverter<M> messageConverter;


    private final Listeners<AdvancedChatListener<M>> listeners = new Listeners<>();

    private Subscription subscription = Subscription.NONE;

    @Getter
    @Setter
    private Duration timeout = Duration.ofSeconds(30);

    private final EventHandler eventHandler = new EventHandler();

    private final ChatSender<M> chatSender;

    private final ChatReceiver<M> chatReceiver;

    public PerococcoAdvancedChat(
            @NonNull Chat chat,
            @NonNull RequestAnswerMatcher<M> matcher,
            @NonNull MessageConverter<M> messageConverter,
            @NonNull Instants instants) {
        this.chat = chat;
        this.messageConverter = messageConverter;
        this.matcher = matcher;
        final BlockingDeque<RequestPostData<?, M>> postDataQueue = new LinkedBlockingDeque<>();
        this.chatSender = new ChatSender<>(chat, listeners, postDataQueue, instants);
        this.chatReceiver = new ChatReceiver<>(matcher::shouldPerformMatching, postDataQueue);
    }

    @Override
    @Synchronized
    public void start() {
        subscription.unsubscribe();
        subscription = chat.addChatListener(this::onChatEvent);
        chat.start();
        chatSender.start();
        chatReceiver.start();
    }

    @Override
    @Synchronized
    public boolean isRunning() {
        return chat.isRunning();
    }

    @Override
    @Synchronized
    public void requestStop() {
        subscription.unsubscribe();
        subscription = Subscription.NONE;
        chatSender.requestStop();
        chatReceiver.requestStop();
        chat.requestStop();
    }


    private void onChatEvent(@NonNull ChatEvent chatEvent) {
        chatEvent.accept(eventHandler);
    }

    @Override
    public @NonNull Subscription addChatListener(@NonNull AdvancedChatListener<M> listener) {
        return listeners.addListener(listener);
    }


    @Override
    public @NonNull CompletionStage<DispatchSlip> sendCommand(@NonNull Command command) {
        return chatSender.send(new CommandPostData<>(command));
    }

    @Override
    public @NonNull <A> CompletionStage<ReceiptSlip<A>> sendRequest(@NonNull Request<A> request) {
        return chatSender.send(new RequestPostData<>(request, matcher));
    }

    private void warnListeners(@NonNull AdvancedChatEvent<M> event) {
        listeners.warnListeners(AdvancedChatListener::onChatEvent, event);
    }


    private class EventHandler implements ChatEventVisitor {

        @Override
        public void visit(@NonNull Connection event) {
            warnListeners(perobobbot.chat.advanced.event.Connection.create());
        }

        @Override
        public void visit(@NonNull Disconnection event) {
            warnListeners(perobobbot.chat.advanced.event.Disconnection.create());
        }

        @Override
        public void visit(@NonNull Error event) {
            warnListeners(perobobbot.chat.advanced.event.Error.with(event.getError()));
        }


        @Override
        public void visit(@NonNull PostedMessage event) {
        }

        @Override
        public void visit(@NonNull perobobbot.chat.core.event.ReceivedMessage event) {
            final Instant receptionTime = event.getReceptionTime();
            Stream.of(event.getMessage().split("\\R"))
                  .map(messageConverter::convert)
                  .flatMap(Optional::stream)
                  .map(m -> new ReceivedMessage<>(receptionTime, m))
                  .forEach(this::dispatchReceivedMessage);
        }

        private void dispatchReceivedMessage(@NonNull ReceivedMessage<M> receivedMessage) {
            listeners.warnListeners(AdvancedChatListener::onChatEvent, receivedMessage);
            chatReceiver.onMessageReception(receivedMessage);
        }


    }
}
