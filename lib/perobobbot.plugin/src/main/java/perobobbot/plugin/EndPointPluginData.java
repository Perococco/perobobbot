package perobobbot.plugin;

import lombok.NonNull;
import lombok.Value;
import org.springframework.web.servlet.HandlerMapping;

public record EndPointPluginData(@NonNull HandlerMapping handlerMapping) implements PerobobbotPluginData {

    @Override
    public <T> @NonNull T accept(@NonNull PerobobbotPluginData.Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
