package perobobbot.server.config.extension;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import perobobbot.data.service.ExtensionService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.extension.AvailableExtensions;

@Component
@RequiredArgsConstructor
public class LaunchAtStart implements ApplicationRunner {

    private final @NonNull AvailableExtensions availableExtensions;

    private final @NonNull @UnsecuredService ExtensionService extensionService;

    @Override
    public void run(ApplicationArguments args) {
        extensionService.updateExtensionList(availableExtensions.getNameOfExtensions());
    }
}
