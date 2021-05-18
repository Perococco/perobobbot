package perobobbot.data.security.permission;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import perobobbot.data.security.DataPermission;
import perobobbot.data.service.BotService;
import perobobbot.data.service.TokenService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.Bot;

import java.io.Serializable;
import java.util.UUID;

@Component
public class JoinedChannelEntityPermissions extends DataEntityPermission {

    private final @NonNull BotService botService;

    public JoinedChannelEntityPermissions(@NonNull @UnsecuredService BotService botService) {
        super("JoinedChannelEntity");
        this.botService = botService;
    }

    @Override
    protected boolean hasPermissionWithObject(@NonNull UserDetails principal, Object targetDomainObject, @NonNull DataPermission permission) {
        return false;
    }

    @Override
    protected boolean hasPermissionWithId(@NonNull UserDetails principal, Serializable targetId, @NonNull DataPermission permission) {
        return switch (permission) {
            case DELETE,READ,UPDATE -> doesNotExistOrIsMyCredential(principal, (UUID)targetId);
            default -> false;
        };
    }

    private boolean doesNotExistOrIsMyCredential(@NonNull UserDetails userDetails, @NonNull UUID uuid) {
        final var joinedChannel = botService.findJoinedChannel(uuid).orElse(null);
        if (joinedChannel == null) {
            return true;
        }
        return joinedChannel.getBot().getOwnerLogin().equals(userDetails.getUsername());
    }
}
