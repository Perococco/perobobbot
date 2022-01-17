package perobobbot.data.security.permission;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.data.security.DataPermission;
import perobobbot.data.service.BotService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.UserService;

import java.io.Serializable;
import java.security.Principal;
import java.util.UUID;

@Component
public class PlatformBotEntityPermissions extends DataEntityPermission {

    private final BotService botService;

    public PlatformBotEntityPermissions(@NonNull @UnsecuredService BotService botService, @NonNull @UnsecuredService UserService userService) {
        super("PlatformBotEntity");
        this.botService = botService;
    }

    @Override
    protected boolean hasPermissionWithObject(@NonNull Principal principal, Object targetDomainObject, @NonNull DataPermission permission) {
        return false;
    }

    @Override
    protected boolean hasPermissionWithId(@NonNull Principal principal, Serializable targetId, @NonNull DataPermission permission) {
        return switch (permission) {
            case DELETE,READ,UPDATE -> isMyBot(principal, (UUID)targetId);
            default -> false;
        };
    }

    private boolean isMyBot(Principal userDetails, UUID uuid) {
        final var bot = botService.findBotOwningPlatformBot(uuid).orElse(null);
        if (bot == null) {
            return false;
        }
        return bot.getOwnerLogin().equals(userDetails.getName());
    }
}
