package perobobbot.server.config.tray;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import perobobbot.lang.ThrowableTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Optional;

@Log4j2
@Component
public class TraySetup implements ApplicationRunner, DisposableBean {

    private TrayIcon trayIcon;
    private SystemTray systemTray;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (GraphicsEnvironment.isHeadless()) {
            LOG.warn("Graphics Environment is headless. No System Tray");
            return;
        }
        SwingUtilities.invokeLater(() -> {
            try {
                doRun();
            } catch (Throwable t) {
                ThrowableTool.interruptThreadIfCausedByInterruption(t);
                LOG.error("Could not add TrayIcon", t);
            }
        });
    }

    private void doRun() throws Exception {
        Toolkit.getDefaultToolkit();
        if (!SystemTray.isSupported()) {
            LOG.warn("System Tray is not supported. No System Tray");
            return;
        }
        this.trayIcon = readIcon();

        final SystemTray tray = SystemTray.getSystemTray();
        final PopupMenu popupMenu = new PopupMenu(" Perobobbot");
        final MenuItem item = new MenuItem("About");
        popupMenu.add(item);

        this.trayIcon.setPopupMenu(popupMenu);

        tray.add(trayIcon);
        this.systemTray = tray;
    }

    @Override
    public void destroy() {
        Optional.ofNullable(systemTray).ifPresent(t -> t.remove(trayIcon));
    }

    private @NonNull TrayIcon readIcon() throws IOException {
        final var image = ImageIO.read(TraySetup.class.getResource("perococco_tray_128.png"));
        final var scaledImage = image.getScaledInstance(16, -1, Image.SCALE_SMOOTH);

        return new TrayIcon(scaledImage);
    }
}
