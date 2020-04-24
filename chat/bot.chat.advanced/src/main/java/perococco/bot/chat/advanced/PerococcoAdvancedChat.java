package perococco.bot.chat.advanced;

import bot.chat.advanced.*;
import bot.chat.advanced.event.AdvancedChatEvent;
import bot.chat.advanced.event.ReceivedMessages;
import bot.chat.core.Chat;
import bot.chat.core.event.Error;
import bot.chat.core.event.*;
import bot.common.lang.Listeners;
import bot.common.lang.Subscription;
import com.google.common.collect.ImmutableList;
import lombok.*;

import java.time.Duration;
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

    @Getter @Setter
    private Duration timeout = Duration.ofSeconds(30);

    private final EventHandler eventHandler = new EventHandler();

    private final Sender<M> sender;

    private final Receiver<M> receiver;

    public PerococcoAdvancedChat(
            @NonNull Chat chat,
            @NonNull RequestAnswerMatcher<M> matcher,
            @NonNull MessageConverter<M> messageConverter) {
        this.chat = chat;
        this.messageConverter = messageConverter;
        this.matcher = matcher;
        final BlockingDeque<RequestPostData<?,M>> postDataQueue = new LinkedBlockingDeque<>();
        this.sender = new Sender<>(chat, listeners, postDataQueue);
        this.receiver = new Receiver<>(matcher::shouldPerformMatching, postDataQueue);
    }

    @Override
    @Synchronized
    public void start() {
        subscription.unsubscribe();
        subscription = chat.addChatListener(this::onChatEvent);
        chat.start();
        sender.start();
        receiver.start();
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
        sender.requestStop();
        receiver.requestStop();
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
        return sender.send(new CommandPostData<>(command));
    }

    @Override
    public @NonNull <A> CompletionStage<ReceiptSlip<A>> sendRequest(@NonNull Request<A> request) {
        return sender.send(new RequestPostData<>(request, matcher));
    }

    private void warnListeners(@NonNull AdvancedChatEvent<M> event) {
        listeners.warnListeners(AdvancedChatListener::onChatEvent,event);
    }


    private class EventHandler implements ChatEventVisitor {

        @Override
        public void visit(@NonNull Connection event) {
            warnListeners(bot.chat.advanced.event.Connection.create());
        }

        @Override
        public void visit(@NonNull Disconnection event) {
            warnListeners(bot.chat.advanced.event.Disconnection.create());
        }

        @Override
        public void visit(@NonNull Error event) {
            warnListeners(bot.chat.advanced.event.Error.with(event.error()));
        }


        @Override
        public void visit(@NonNull PostedMessage event) {}

        @Override
        public void visit(@NonNull ReceivedMessage event) {
            final ImmutableList<M> messages = Stream.of(event.message().split("\\R"))
                  .map(messageConverter::convert)
                  .flatMap(Optional::stream)
                  .collect(ImmutableList.toImmutableList());

            if (!messages.isEmpty()) {
                final ReceivedMessages<M> receivedMessages = new ReceivedMessages<>(event.receptionTime(), messages);
                listeners.warnListeners(AdvancedChatListener::onChatEvent, receivedMessages);
                receiver.onMessageReception(receivedMessages);
            }
        }


    }
}
