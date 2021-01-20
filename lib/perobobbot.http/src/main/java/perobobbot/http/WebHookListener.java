package perobobbot.http;

import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface WebHookListener {

    /**
     * @param path the observed path (used when registering this listener)
     * @param method the method of the call
     * @param request the request on this webhook
     * @param response the response to send
     * @throws IOException
     */
    void onCall(@NonNull String path,
                @NonNull RequestMethod method,
                @NonNull HttpServletRequest request,
                @NonNull HttpServletResponse response) throws IOException;

}
