package perobobbot.rest.controller;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.DataCredentialInfo;
import perobobbot.data.com.User;
import perobobbot.data.service.CredentialService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.UserService;
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
    public @NonNull ImmutableList<User> listAllUsers() {
        return userService.listAllUser();
    }

    @PostMapping("")
    public @NonNull User getUserByLogin(@NonNull @RequestBody CreateUserParameters parameters) {
        return userService.createUser(parameters);
    }

    @GetMapping("/{login}")
    public @NonNull User getUserByLogin(@NonNull @PathVariable String login) {
        return userService.getUser(login);
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
