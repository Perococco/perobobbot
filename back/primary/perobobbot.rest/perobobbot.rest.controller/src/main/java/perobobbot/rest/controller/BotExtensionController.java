package perobobbot.rest.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.com.BotExtension;
import perobobbot.data.com.UpdateBotExtensionParameters;
import perobobbot.data.service.ExtensionService;
import perobobbot.data.service.SecuredService;

import java.util.UUID;

@RestController
@RequestMapping("/api/bot-extensions")
@RequiredArgsConstructor
public class BotExtensionController {

    private final @NonNull @SecuredService ExtensionService extensionService;

    @PutMapping("/{botId}/{extensionId}")
    public @NonNull BotExtension updateExtension(
            @NonNull @PathVariable(name = "botId") UUID botId,
            @NonNull @PathVariable(name = "extensionId") UUID extensionId,
            @NonNull @RequestBody UpdateBotExtensionParameters parameters) {
        return extensionService.updateBotExtension(botId,extensionId,parameters);
    }


}


