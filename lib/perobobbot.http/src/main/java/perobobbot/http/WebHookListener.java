package perobobbot.http;

import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface WebHookListener {

    void onCall(@NonNull String path, @NonNull RequestMethod method, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws IOException;


    WebHookListener NOP = (p,m,rq,rp) -> {};
}
