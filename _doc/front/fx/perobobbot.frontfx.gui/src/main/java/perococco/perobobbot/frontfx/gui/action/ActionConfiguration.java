package perococco.perobobbot.frontfx.gui.action;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.action.ActionExecutor;
import perobobbot.action.ActionFilter;
import perobobbot.action.ActionManager;
import perobobbot.frontfx.action.FxClientActionTool;
import perobobbot.frontfx.model.FXApplicationIdentity;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Log4j2
public class ActionConfiguration implements DisposableBean {

    private final FxClientActionTool fxClientActionTool;

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor(
            new ThreadFactoryBuilder().setNameFormat("Action Executor %d").setDaemon(false).build()
    );

    public ActionConfiguration(@NonNull ApplicationContext applicationContext,
                               @NonNull FXApplicationIdentity fxApplicationIdentity) {
        this.fxClientActionTool = new FxClientActionTool(
                fxApplicationIdentity,
                EXECUTOR,
                new ActionProviderWithContext(applicationContext),
                actionFilters(applicationContext)
        );
    }

    @Bean
    public ActionManager actionManager() {
        return fxClientActionTool.getActionManager();
    }

    @Bean
    public ActionExecutor actionExecutor() {
        return fxClientActionTool.getActionExecutor();
    }

    @NonNull
    private static ImmutableList<ActionFilter> actionFilters(@NonNull ApplicationContext applicationContext) {
        final Map<String, ActionFilter> actionFilters = applicationContext.getBeansOfType(ActionFilter.class);
        return ImmutableList.copyOf(actionFilters.values());
    }

    @Override
    public void destroy() throws Exception {
        LOG.info("Shutting down Action Executor");
        EXECUTOR.shutdown();
    }

}
