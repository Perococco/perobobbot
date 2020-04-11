package perococco.bot.chat.websocket;

import bot.chat.core.ChatBase;
import bot.chat.core.MessagePostingFailure;
import bot.common.lang.ThrowableTool;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * @author perococco
 **/
@Log4j2
public class WebSocketChat extends ChatBase {

    private static final Marker MARKER = MarkerManager.getMarker("WEBSOCKET_CHAT").addParents(MarkerManager.getMarker("CHAT"));

    @NonNull
    private final Session session;

    @SuppressWarnings("Convert2Lambda") //cannot be converted to lambda du to visibility issue
    private final MessageHandler.Whole<String> messageHandler = new MessageHandler.Whole<>() {
        @Override
        public void onMessage(String message) {
            Stream.of(message.split("\\R")).forEach(WebSocketChat.this::warnListenerOnReceivedMessage);
        }
    };

    public WebSocketChat(@NonNull Session session) {
        this.session = session;
        this.session.addMessageHandler(messageHandler);
    }

    public void dispose(){
        this.session.removeMessageHandler(messageHandler);
        try {
            this.session.close();
        } catch (Exception e) {
            ThrowableTool.isCausedByAnInterruption(e);
            LOG.warn(MARKER,"Error while closing session",e);
        }
    }

    @Override
    public void postMessage(@NonNull String message) {
        try {
            this.doPostMessage(message);
        } catch (Exception e) {
            warnListenerOnError(e);
            throw e;
        }
    }

    private void doPostMessage(@NonNull String message) {
        try {
            session.getBasicRemote().sendText(message);
            warnListenerOnPostMessage(message);
        } catch (IOException e) {
            LOG.warn(MARKER,() -> String.format("Could not post message '%s'",message),e);
            throw new MessagePostingFailure(message,e);
        }
    }
}
