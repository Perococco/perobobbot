package perobobbot.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Type;
import perobobbot.lang.*;
import perobobbot.lang.client.EncryptedClient;
import perobobbot.lang.client.EncryptedTwitchClient;
import perobobbot.lang.token.EncryptedClientToken;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue(PlatfomId.TWITCH)
public class TwitchClientEntity extends ClientEntity {

    public TwitchClientEntity(String clientId, String clientSecret) {
        super(Platform.TWITCH, clientId, clientSecret);
    }

    @Override
    public @NonNull EncryptedTwitchClient toView() {
        return EncryptedTwitchClient.builder()
                                    .id(getUuid())
                                    .platform(getPlatform())
                                    .clientId(getClientId())
                                    .clientSecret(this.getClientSecret())
                                    .build();
    }
}
