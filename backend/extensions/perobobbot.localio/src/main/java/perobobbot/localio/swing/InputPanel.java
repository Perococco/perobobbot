package perobobbot.localio.swing;

import lombok.NonNull;
import perobobbot.lang.Listeners;
import perobobbot.lang.Subscription;
import perobobbot.lang.fp.Consumer1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

public class InputPanel extends JPanel {

    private final JTextField inputField;

    private final Listeners<Consumer1<String>> inputListeners = new Listeners<>();

    public InputPanel() {
        super(new BorderLayout());
        this.inputField = new JTextField(30);
        this.inputField.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAction();
            }
        });

        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        this.inputField.setFont(inputField.getFont().deriveFont(24.f));
        this.add(inputField,BorderLayout.CENTER);
    }

    public @NonNull Subscription addListener(@NonNull Consumer1<String> listener) {
        return inputListeners.addListener(listener);
    }

    public void performAction() {
        final String line = Optional.ofNullable(inputField.getText())
                                    .map(String::trim)
                                    .orElse("");
        inputField.setText("");
        if (line.isBlank()) {
            return;
        }
        inputListeners.warnListeners(c -> c.accept(line));
    }

}
