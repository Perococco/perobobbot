package perobobbot.rest.controller;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.service.BotService;
import perobobbot.data.service.SecuredService;
import perobobbot.lang.Bot;
import perobobbot.rest.com.CreateBotParameters;
import perobobbot.security.com.RoleKind;

import java.util.UUID;

@RestController
@RequestMapping("/api/bots")
@RequiredArgsConstructor
public class BotController {

    public static final String ADMIN_ROLE = "ROLE_" + RoleKind.ADMIN.name();

    private final @NonNull
    @SecuredService
    BotService botService;

    @DeleteMapping("/{id}")
    public void deleteBot(@PathVariable @NonNull UUID id) {
        botService.deleteBot(id);
    }

    @GetMapping("")
    public ImmutableList<Bot> listBots(@AuthenticationPrincipal UserDetails principal) {
        var isAdmin = principal.getAuthorities()
                               .stream()
                               .map(GrantedAuthority::getAuthority)
                               .anyMatch(ADMIN_ROLE::equals);
        if (isAdmin) {
            return botService.listAllBots();
        } else {
            return botService.listBots(principal.getUsername());
        }
    }

    @GetMapping("/{login}")
    public ImmutableList<Bot> listUserBots(@NonNull @PathVariable String login) {
        return botService.listBots(login);
    }

    @PostMapping("")
    public Bot createBot(@AuthenticationPrincipal UserDetails principal, @RequestBody CreateBotParameters parameters) {
        return botService.createBot(principal.getUsername(), parameters.getName());
    }
}
