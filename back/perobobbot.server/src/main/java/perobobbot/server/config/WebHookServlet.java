package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMethod;
import perobobbot.http.WebHookDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class WebHookServlet extends HttpServlet {

    private final @NonNull WebHookDispatcher webHookDispatcher;

    private void doMethod(@NonNull RequestMethod method, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws ServletException,IOException {
        webHookDispatcher.dispatch(request.getPathInfo(),method,request,response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doMethod(RequestMethod.GET,req, resp);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doMethod(RequestMethod.HEAD,req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doMethod(RequestMethod.POST,req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doMethod(RequestMethod.PUT,req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doMethod(RequestMethod.DELETE,req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doMethod(RequestMethod.OPTIONS,req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doMethod(RequestMethod.TRACE,req, resp);
    }
}
