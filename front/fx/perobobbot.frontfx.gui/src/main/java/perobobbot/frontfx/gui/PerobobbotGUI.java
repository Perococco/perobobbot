package perobobbot.frontfx.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import perobobbot.lang.ThrowableTool;
import perobobbot.lang.Todo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PerobobbotGUI extends Application {

    private static final Map<String, Data> callbacks = new HashMap<>();

    public static void main(@NonNull ApplicationContext context, @NonNull CompletableFuture<GUIInfo> callback) {
        final String id = UUID.randomUUID().toString();
        callbacks.put(id,new Data(context,callback));
        launch(id);
    }

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        final var data = callbacks.get(getParameters().getRaw().get(0));
        try {
            final var label = new Label("Hello");
            stage.setScene(new Scene(label, 640, 480));

            data.callback.complete(new GUIInfo(stage));
            stage.show();
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            data.callback.completeExceptionally(t);
        }
    }

    @RequiredArgsConstructor
    private static class Data {

        private final @NonNull ApplicationContext applicationContext;

        private final @NonNull CompletableFuture<GUIInfo> callback;
    }
}
