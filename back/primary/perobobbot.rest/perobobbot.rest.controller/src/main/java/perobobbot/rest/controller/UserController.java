package perobobbot.rest.controller;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.UpdateUserParameters;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.data.service.OAuthService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.SubscriptionService;
import perobobbot.data.service.UserService;
import perobobbot.eventsub.EventSubManager;
import perobobbot.lang.ListTool;
import perobobbot.rest.com.RestUserToken;
import perobobbot.security.com.SimpleUser;
import perobobbot.security.com.User;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final @NonNull
    @SecuredService
    UserService userService;

    private final @NonNull
    @SecuredService
    OAuthService oauthService;


    @GetMapping("")
    public @NonNull ImmutableList<SimpleUser> listAllUsers() {
        return ListTool.map(userService.listAllUser(), User::simplify);
    }

    @GetMapping("/")
    public @NonNull String sayHello() {
        return "HELLO";
    }

    @PostMapping("")
    public @NonNull SimpleUser createUser(@NonNull @RequestBody CreateUserParameters parameters) {
        return userService.createUser(parameters).simplify();
    }

    @GetMapping("/{login}")
    public @NonNull SimpleUser getUserByLogin(@NonNull @PathVariable String login) {
        return userService.getUser(login).simplify();
    }

    @PatchMapping ("/{login}")
    public @NonNull SimpleUser updateUser(@NonNull @PathVariable String login, @RequestBody @NonNull UpdateUserParameters parameters) {
        return userService.updateUser(login,parameters).simplify();
    }

    @GetMapping("/{login}/tokens")
    public @NonNull ImmutableList<RestUserToken> getUserTokens(@NonNull @PathVariable String login) {
        return oauthService.getAllUserTokens(login)
                           .stream()
                           .map(RestUserToken::fromUserTokenView)
                           .collect(ImmutableList.toImmutableList());
    }

}
