package perobobbot.server.controller;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import perobobbot.extension.ExtensionManager;

@RestController
@RequestMapping("/api/extensions")
@RequiredArgsConstructor
public class ExtensionController {

    private final @NonNull ExtensionManager extensionManager;

    @GetMapping()
    public ImmutableSet<String> listExtensions() {
        return extensionManager.listAllExtensions();
    }
}
