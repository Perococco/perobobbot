package perobobbot.rest.controller;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import perobobbot.data.com.BotExtension;
import perobobbot.data.com.Extension;
import perobobbot.data.service.ExtensionService;
import perobobbot.data.service.SecuredService;
import perobobbot.data.com.UpdateExtensionParameters;

import java.util.UUID;

@RestController
@RequestMapping("/api/extensions")
@RequiredArgsConstructor
public class ExtensionController {

    private final @NonNull @SecuredService ExtensionService extensionService;

    @GetMapping("")
    public ImmutableList<Extension> listExtensions() {
        return extensionService.listAllExtensions();
    }

    @PutMapping("/{id}")
    public @NonNull Extension updateExtension(@NonNull @PathVariable(name = "id") UUID extensionId, @NonNull @RequestBody UpdateExtensionParameters parameters) {
        return extensionService.updateExtension(extensionId, parameters);
    }


}


