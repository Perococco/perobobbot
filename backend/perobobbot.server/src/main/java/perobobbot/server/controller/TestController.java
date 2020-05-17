package perobobbot.server.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import perobobbot.data.com.UserDTO;
import perobobbot.data.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TestController {

    @NonNull
    private final UserService userService;


    @GetMapping(value = "/user")
    public UserDTO getCities(@RequestParam(name = "login") String login) {
        final UserDTO dto = userService.getUserInfo(login);
        return dto;
    }
}
