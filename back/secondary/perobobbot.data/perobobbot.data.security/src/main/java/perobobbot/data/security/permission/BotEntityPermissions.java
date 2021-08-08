package perobobbot.data.security.permission;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.data.security.DataPermission;
import perobobbot.data.service.BotService;
import perobobbot.data.service.UnsecuredService;

import java.io.Serializable;
import java.security.Principal;
import java.util.UUID;

@Component
public class BotEntityPermissions extends DataEntityPermission {

    private final BotService botService;

    public BotEntityPermissions(@NonNull @UnsecuredService BotService botService) {
        super("BotEntity");
        this.botService = botService;
    }

    @Override
    protected boolean hasPermissionWithObject(@NonNull Principal principal, Object targetDomainObject, @NonNull DataPermission permission) {
        return false;
    }

    @Override
    protected boolean hasPermissionWithId(@NonNull Principal principal, Serializable targetId, @NonNull DataPermission permission) {
        return switch (permission) {
            case DELETE,READ,UPDATE -> doesNotExistOrIsMyBot(principal, (UUID)targetId);
            default -> false;
        };
    }

    private boolean doesNotExistOrIsMyBot(Principal userDetails, UUID uuid) {
        final var bot = botService.findBot(uuid).orElse(null);
        if (bot == null) {
            return true;
        }
        return bot.getOwnerLogin().equals(userDetails.getName());
    }
}
