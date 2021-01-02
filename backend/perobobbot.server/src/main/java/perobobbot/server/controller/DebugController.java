package perobobbot.server.controller;

import com.google.common.collect.ImmutableSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import perobobbot.data.com.Role;
import perobobbot.data.com.SimpleUser;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @GetMapping("/user")
    private SimpleUser debug() {
        return new SimpleUser("hello", ImmutableSet.of());
    }

}
