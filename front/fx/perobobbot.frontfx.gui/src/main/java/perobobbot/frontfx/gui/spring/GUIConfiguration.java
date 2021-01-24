package perobobbot.frontfx.gui.spring;

import javafx.application.Platform;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.gui.GUIInfo;
import perobobbot.frontfx.gui.PerobobbotGUI;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

//@Component
@Log4j2
@Order(-20)
@RequiredArgsConstructor
public class GUIConfiguration implements ApplicationRunner, DisposableBean {

    private final @NonNull ApplicationContext applicationContext;

    private GUIInfo guiInfo;

    public static Plugin provider() {
        return Plugin.with(PluginType.PRIMARY,"FX Gui",GUIConfiguration.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final var future = new CompletableFuture<GUIInfo>();
        new Thread(() -> launchApplication(applicationContext,future),"FX GUI Launcher").start();
        future.whenComplete((r,t) -> {
            if (t!=null) {
                LOG.error("Error while launching the GUI",t);
            }
            guiInfo = r;
        });
    }

    private void launchApplication(@NonNull ApplicationContext context, @NonNull CompletableFuture<GUIInfo> callback) {
        PerobobbotGUI.main(context,callback);
    }

    public void destroy() {
        Optional.ofNullable(guiInfo).ifPresent(r -> Platform.runLater(() -> r.getPrimaryStage().close()));
    }
}
