package perobobbot.rest.controller;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import perobobbot.lang.NoTypeScript;

@NoTypeScript
@RestController
@Log4j2
public class MiscController {

    // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
    // Required because of 'mode: history' usage in frontend routing, see README for further details
    @RequestMapping(value = "{_:^(?!index\\.html|api).*$}")
    public String frontEndRedirectApi() {
        LOG.info("URL entered directly into the Browser, so we need to redirect...");
        return "forward:/";
    }

    @RequestMapping(value = "/api/ping")
    public @NonNull String ping(@RequestParam(name = "secret") @NonNull String secret) {
        return secret;
    }

}
