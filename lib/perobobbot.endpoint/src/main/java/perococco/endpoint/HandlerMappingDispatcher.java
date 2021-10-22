package perococco.endpoint;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
public class HandlerMappingDispatcher implements HandlerMapping {

    private final ImmutableList<HandlerMapping> handlerMappings;

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        HandlerExecutionChain result;
        for (HandlerMapping handlerMapping : handlerMappings) {
            result = handlerMapping.getHandler(request);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

}
