package perobobbot.rest.controller;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import perobobbot.data.com.Extension;
import perobobbot.data.service.ExtensionService;
import perobobbot.data.service.SecuredService;

@RestController
@RequestMapping("/api/extensions")
@RequiredArgsConstructor
public class ExtensionController {

    private final @NonNull @SecuredService ExtensionService extensionService;

    @GetMapping("")
    public ImmutableList<Extension> listExtensions() {
        return extensionService.listAllExtensions();
    }
}
