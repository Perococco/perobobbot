package perobobbot.data.jpa.service.permission;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import perobobbot.data.domain.BotEntity;
import perobobbot.data.jpa.repository.BotRepository;
import perobobbot.data.jpa.service.DataOperation;

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
    protected boolean hasPermission(@NonNull UserDetails userDetails, Object targetDomainObject, DataOperation permission) {
        return false;
    }

    @Override
    protected boolean hasPermission(@NonNull UserDetails userDetails, Serializable targetId, String targetType, DataOperation permission) {
        return switch (permission) {
            case DELETE,READ,UPDATE -> doesNotExistOrIsMyBot(userDetails, (UUID)targetId);
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
