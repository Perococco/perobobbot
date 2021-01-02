package perobobbot.server.controller;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.service.BotService;
import perobobbot.lang.Bot;
import perobobbot.server.dto.CreateBotParameters;

import java.util.UUID;

@RestController
@RequestMapping("/api/bots")
@RequiredArgsConstructor
public class BotController {

    private final @NonNull BotService botService;

    @DeleteMapping("/{id}")
    public void deleteBot(@PathVariable @NonNull UUID id) {
        botService.deleteBot(id);
    }

    @GetMapping("/")
    public ImmutableList<Bot> listBots(@AuthenticationPrincipal UserDetails principal) {
        return botService.getBots(principal.getUsername());
    }

    @GetMapping("/{login}")
    public ImmutableList<Bot> listBots(@PathVariable @NonNull String login) {
        return botService.getBots(login);
    }

    @PostMapping("/")
    public Bot createBot(@AuthenticationPrincipal UserDetails principal, @RequestBody CreateBotParameters parameters) {
        return botService.createBot(principal.getUsername(), parameters.getName());
    }
}
