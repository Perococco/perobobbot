package perobobbot.plugin;

import lombok.NonNull;
import lombok.Value;

@Value
public class ViewInfo {

    @NonNull String urlPathOrPattern;

    @NonNull String viewName;
}
