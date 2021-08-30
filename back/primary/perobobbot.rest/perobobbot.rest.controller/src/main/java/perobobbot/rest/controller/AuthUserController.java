package perobobbot.rest.controller;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import perobobbot.data.com.BotExtension;
import perobobbot.data.service.ExtensionService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.UserService;
import perobobbot.security.com.BotUser;
import perobobbot.security.com.EndPoints;
import perobobbot.security.com.SimpleUser;


@RestController
@RequestMapping("/api"+EndPoints.CURRENT_USER)
@RequiredArgsConstructor
@Log4j2
@Validated
public class AuthUserController {

    @NonNull @SecuredService
    UserService userService;

    @NonNull @SecuredService
    ExtensionService extensionService;

    /**
     * @param principal the principal provided by the security framework if an user is authenticated
     * @return the authenticated user information
     */
    @GetMapping("")
    public SimpleUser getCurrentUser(@AuthenticationPrincipal BotUser principal) {
        return userService.getUser(principal.getUsername()).simplify();
    }

    @GetMapping("/extensions")
    public ImmutableList<BotExtension> getExtensions(@AuthenticationPrincipal BotUser principal) {
        final var login = principal.getUsername();
        return extensionService.listAllUserExtensions(login);
    }


}
