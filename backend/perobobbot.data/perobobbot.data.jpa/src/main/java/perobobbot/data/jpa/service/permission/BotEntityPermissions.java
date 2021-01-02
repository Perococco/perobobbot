package perobobbot.data.jpa.service.permission;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import perobobbot.data.domain.BotEntity;
import perobobbot.data.jpa.repository.BotRepository;
import perobobbot.data.jpa.service.DataPermission;

import java.io.Serializable;
import java.util.UUID;

@Component
public class BotEntityPermissions extends DataEntityPermission {

    private final @NonNull BotRepository botRepository;

    public BotEntityPermissions(@NonNull BotRepository botRepository) {
        super(BotEntity.class);
        this.botRepository = botRepository;
    }

    @Override
    protected boolean hasPermissionWithObject(@NonNull UserDetails principal, Object targetDomainObject, @NonNull DataPermission permission) {
        return false;
    }

    @Override
    protected boolean hasPermissionWithId(@NonNull UserDetails principal, Serializable targetId, @NonNull DataPermission permission) {
        return switch (permission) {
            case DELETE,READ,UPDATE -> doesNotExistOrIsMyBot(principal, (UUID)targetId);
            default -> false;
        };
    }

    private boolean doesNotExistOrIsMyBot(UserDetails userDetails, UUID uuid) {
        final var bot = botRepository.findByUuid(uuid).orElse(null);
        if (bot == null) {
            return true;
        }
        return userDetails.getUsername().equals(bot.getOwnerLogin());
    }
}
