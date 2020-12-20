package perobobbot.localio;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.lang.*;
import perobobbot.localio.swing.InputPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class LocalChatPlatform implements ChatPlatform {

    private final @NonNull ApplicationCloser applicationCloser;
    private final @NonNull StandardInputProvider standardInputProvider;

    private final @NonNull LocalSender localSender = new ToStandardOutputSender();

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();
    private volatile @NonNull ImmutableMap<Bot, LocalConnection> localConnections = ImmutableMap.of();
    private final @NonNull Listeners<MessageListener> listeners = new Listeners<>();
    private final LocalExecutor localExecutor;

    private final PrintStream output = System.out;
    private JFrame dialog = null;
    private final SubscriptionHolder guiSubscription = new SubscriptionHolder();

    public LocalChatPlatform(@NonNull ApplicationCloser applicationCloser, @NonNull StandardInputProvider standardInputProvider) {
        this.applicationCloser = applicationCloser;
        this.standardInputProvider = standardInputProvider;
        this.localExecutor = new LocalExecutor(output, this::onLocalMessages,
                                               LocalAction.with("stop", "stop the server", this::stopServer),
                                               LocalAction.with("show-gui", "show the gui", this::showGui),
                                               LocalAction.with("hide-gui", "hide the gui", this::hideGui)
        );
        this.subscriptionHolder.replaceWith(() -> standardInputProvider.addListener(localExecutor::handleMessage));
    }

    private void stopServer() {
        applicationCloser.execute();
    }

    private void onLocalMessages(@NonNull LocalMessage localMessage) {
        findTargetedBot(localMessage)
                .ifPresent(b ->
                           {
                               final MessageContext ctx = MessageContext.builder()
                                                                        .bot(b)
                                                                        .messageFromMe(false)
                                                                        .content(localMessage.getMessage())
                                                                        .rawPayload(localMessage.getMessage())
                                                                        .messageOwner(LocalChat.LOCAL_USER)
                                                                        .receptionTime(Instant.now())
                                                                        .channelInfo(LocalChat.CONSOLE_CHANNEL_INFO)
                                                                        .build();
                               listeners.warnListeners(l -> l.onMessage(ctx));
                           }
                );
    }

    private @NonNull Optional<Bot> findTargetedBot(@NonNull LocalMessage localMessage) {
        final var connections = this.localConnections;
        if (localMessage.getBotName().isBlank()) {
            if (connections.isEmpty()) {
                return Optional.empty();
            } else if (connections.size() == 1) {
                return connections.keySet().stream().findFirst();
            } else {
                output.println("Bot name must be provided when multiple bots are registered: #botname message");
                return Optional.empty();
            }
        } else {
            final var bot = connections.keySet()
                                       .stream()
                                       .filter(b -> b.getName().equalsIgnoreCase(localMessage.getBotName()))
                                       .findFirst();
            if (bot.isEmpty()) {
                output.println("Unknown bot '"+localMessage.getBotName()+"'");
            }
            return bot;
        }

    }

    private void showHelp() {
        System.out.println("stop -> stop the server");
        System.out.println("");
    }

    private void dispatchInputMessage(String line) {
        if (line.startsWith("#")) {

        }
    }

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.LOCAL;
    }

    @Override
    public @NonNull CompletionStage<? extends ChatConnection> connect(@NonNull Bot bot) {
        return CompletableFuture.completedFuture(getLocalConnection(bot));
    }

    @Override
    public @NonNull Optional<CompletionStage<? extends ChatConnection>> findConnection(@NonNull Bot bot) {
        return Optional.of(CompletableFuture.completedFuture(getLocalConnection(bot)));
    }

    @Synchronized
    private @NonNull LocalConnection getLocalConnection(@NonNull Bot bot) {
        final LocalConnection existingConnection = localConnections.get(bot);
        if (existingConnection != null) {
            return existingConnection;
        }
        final LocalConnection newConnection = this.createLocalConnectionForBot(bot);
        this.localConnections = MapTool.add(this.localConnections, bot, newConnection);
        return newConnection;
    }

    private @NonNull LocalConnection createLocalConnectionForBot(@NonNull Bot bot) {
        return new LocalConnection(bot, localSender);
    }

    @Override
    public @NonNull Subscription addMessageListener(@NonNull MessageListener listener) {
        return listeners.addListener(listener);
    }

    @Override
    @Synchronized
    public void dispose() {
        localConnections.values().forEach(LocalConnection::dispose);
        localConnections = ImmutableMap.of();
    }

    public void showGui() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::showGui);
            return;
        }
        if (GraphicsEnvironment.isHeadless() || dialog != null) {
            return;
        }
        final InputPanel inputPanel = new InputPanel();
        this.guiSubscription.replaceWith(() -> inputPanel.addListener(localExecutor::handleMessage));
        this.dialog = new JFrame("Local Command");
        this.dialog.getContentPane().add(inputPanel);
        this.dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                guiSubscription.unsubscribe();
                dialog = null;
            }
        });
        this.dialog.pack();
        this.dialog.setVisible(true);
        this.dialog.setAlwaysOnTop(true);
        this.dialog.toFront();
    }

    public void hideGui() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::hideGui);
            return;
        }
        if (dialog == null) {
            return;
        }
        this.guiSubscription.unsubscribe();
        this.dialog.dispose();
        this.dialog = null;
    }

}
