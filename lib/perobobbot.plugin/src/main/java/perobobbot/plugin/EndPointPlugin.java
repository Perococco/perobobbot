package perobobbot.plugin;

import jplugman.annotation.ExtensionPoint;
import lombok.NonNull;
import org.springframework.web.servlet.HandlerMapping;

@ExtensionPoint(version = 1)
public interface EndPointPlugin extends PerobobbotPlugin {

    @NonNull HandlerMapping getHandlerMapping();

    @Override
    default <T> @NonNull T accept(@NonNull PerobobbotPlugin.Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
