package perobobbot.rest.controller;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.DataCredentialInfo;
import perobobbot.data.com.UpdateUserParameters;
import perobobbot.security.com.SimpleUser;
import perobobbot.security.com.User;
import perobobbot.data.service.CredentialService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.UserService;
import perobobbot.lang.ListTool;
import perobobbot.rest.com.RestCredentialInfo;
import perobobbot.rest.controller.converter.CredentialInfoConverter;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final @NonNull
    @SecuredService
    UserService userService;

    private final @NonNull
    @SecuredService
    CredentialService credentialService;


    @GetMapping("")
    public @NonNull ImmutableList<SimpleUser> listAllUsers() {
        return ListTool.map(userService.listAllUser(), User::simplify);
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

    @GetMapping("/{login}/credentials")
    public @NonNull ImmutableList<RestCredentialInfo> getUserCredentials(@NonNull @PathVariable String login) {
        return credentialService.getUserCredentials(login)
                                .stream()
                                .filter(DataCredentialInfo::hasSecret)
                                .map(CredentialInfoConverter.INSTANCE)
                                .collect(ImmutableList.toImmutableList());
    }

}
