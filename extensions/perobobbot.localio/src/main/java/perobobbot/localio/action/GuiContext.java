package perobobbot.localio.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.localio.LocalExecutor;
import perobobbot.localio.swing.InputPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@RequiredArgsConstructor
public class GuiContext {

    private final @NonNull LocalExecutor localExecutor;
    private final @NonNull SubscriptionHolder guiSubscription = new SubscriptionHolder();
    private JFrame dialog = null;


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
