package perobobbot.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import perobobbot.data.com.UserDTO;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @GetMapping("/user")
    private UserDTO debug() {
        return new UserDTO("hello");
    }

}
