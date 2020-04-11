package perococco.bot.chat.advanced;

import bot.chat.advanced.*;
import bot.chat.core.Chat;
import bot.common.lang.Listeners;
import bot.common.lang.Nil;
import bot.common.lang.Subscription;
import lombok.NonNull;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author perococco
 **/
public class PerococcoAdvancedChat implements AdvancedChat {

    private final Listeners<AdvancedChatListener> listeners = new Listeners<>();

    @NonNull
    private final BlockingDeque<PostData<?>> postDataQueue = new LinkedBlockingDeque<>();

    private final Sender sender;

    private final Receiver receiver;

    private final RequestAnswerMatcher matcher;

    public PerococcoAdvancedChat(@NonNull Chat chat, @NonNull RequestAnswerMatcher matcher, @NonNull MessageConverter messageFactory) {
        this.matcher = matcher;
        final BlockingDeque<RequestPostData<?>> requestPostData = new LinkedBlockingDeque<>();
        this.sender = new Sender(chat,listeners,requestPostData,postDataQueue);
        this.receiver = new Receiver(chat,listeners,requestPostData,messageFactory);
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
    public CompletionStage<Nil> sendCommand(@NonNull Command command) {
        return send(new CommandPostData(command));
    }

    @Override
    public @NonNull <A> CompletionStage<A> sendRequest(@NonNull Request<A> request) {
        return send(new RequestPostData<>(request,matcher));
    }

    private <A> CompletionStage<A> send(@NonNull PostData<A> postData) {
        postDataQueue.offerLast(postData);
        return postData.completionStage();
    }

    @Override
    public @NonNull Subscription addChatListener(@NonNull AdvancedChatListener listener) {
        return listeners.addListener(listener);
    }
}
