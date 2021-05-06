package perobobbot.oauth;

import lombok.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

public interface OAuthListener {

    /**
     * @param redirectURI the URI used to redirect the oauth process to the bot
     * @param request the request on this webhook
     * @param response the response to send
     * @throws IOException
     */
    void onCall(@NonNull URI redirectURI,
                @NonNull HttpServletRequest request,
                @NonNull HttpServletResponse response) throws IOException;

    void onTimeout();
}
