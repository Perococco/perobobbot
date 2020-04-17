package perococco.bot.chat.advanced;

import bot.chat.advanced.*;
import bot.chat.core.Chat;
import bot.common.lang.Listeners;
import bot.common.lang.Nil;
import bot.common.lang.Subscription;
import lombok.NonNull;

import java.time.Duration;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author perococco
 **/
public class PerococcoAdvancedChat<M> implements AdvancedChat<M> {

    private final Listeners<AdvancedChatListener<M>> listeners = new Listeners<>();

    @NonNull
    private final BlockingDeque<PostData<?,M>> postDataQueue = new LinkedBlockingDeque<>();

    private final Sender<M> sender;

    private final Receiver<M> receiver;

    private final RequestAnswerMatcher<M> matcher;

    public PerococcoAdvancedChat(@NonNull Chat chat, @NonNull RequestAnswerMatcher<M> matcher, @NonNull MessageConverter<M> messageFactory) {
        this.matcher = matcher;
        final BlockingDeque<RequestPostData<?,M>> requestPostData = new LinkedBlockingDeque<>();
        this.sender = new Sender<>(chat,listeners,requestPostData,postDataQueue);
        this.receiver = new Receiver<>(chat,listeners,requestPostData,messageFactory,matcher::shouldPerformMatching);
    }

    public void start() {
        this.receiver.start();
        this.sender.start();
    }

    public void stop() {
        this.sender.requestStop();
        this.receiver.requestStop();
    }

    @NonNull
    @Override
    public CompletionStage<DispatchSlip> sendCommand(@NonNull Command command) {
        return send(new CommandPostData<>(command));
    }

    @Override
    public @NonNull <A> CompletionStage<ReceiptSlip<A>> sendRequest(@NonNull Request<A> request) {
        return send(new RequestPostData<>(request,matcher));
    }

    private <A> CompletionStage<A> send(@NonNull PostData<A,M> postData) {
        postDataQueue.offerLast(postData);
        return postData.completionStage();
    }

    @Override
    public @NonNull Subscription addChatListener(@NonNull AdvancedChatListener<M> listener) {
        return listeners.addListener(listener);
    }

    @Override
    public @NonNull Duration timeout() {
        return sender.timeout();
    }

    @Override
    public void timeout(@NonNull Duration duration) {
        sender.timeout(duration);
    }
}
