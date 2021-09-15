package perobobbot.server.plugin.webplugin;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class CompositeHandlerMapping implements HandlerMapping {

    private final @NonNull ImmutableList<HandlerMapping> handlerMappings;

    @Override
    public HandlerExecutionChain getHandler(@NonNull HttpServletRequest request) throws Exception {
        HandlerExecutionChain result = null;
        for (HandlerMapping handlerMapping : handlerMappings) {
            result = handlerMapping.getHandler(request);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
