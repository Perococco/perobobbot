package perobobbot.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Type;
import perobobbot.data.com.InvalidClientType;
import perobobbot.lang.client.DecryptedClient;
import perobobbot.lang.client.EncryptedClient;
import perobobbot.lang.Platform;
import perobobbot.lang.TextEncryptor;
import perobobbot.lang.fp.Function2;
import perobobbot.lang.token.EncryptedBotToken;
import perobobbot.lang.token.EncryptedClientToken;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "CLIENT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PLATFORM")
@NoArgsConstructor
@Getter
@Setter
public abstract class ClientEntity extends PersistentObjectWithUUID {

    @Column(name = "PLATFORM", insertable = false, updatable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private Platform platform;

    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "CLIENT_SECRET")
    private String clientSecret;

    @OneToMany(mappedBy = "client")
    private List<ClientTokenEntity> clientTokens = new ArrayList<>();

    public ClientEntity(Platform platform, String clientId, String clientSecret) {
        super(UUID.randomUUID());
        this.platform = platform;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public abstract @NonNull EncryptedClient toView();

    public abstract @NonNull Optional<EncryptedBotToken> getBotTokenView();

    public @NonNull DecryptedClient toDecryptedView(@NonNull TextEncryptor textEncryptor) {
        return toView().decrypt(textEncryptor);
    }


    public @NonNull ClientTokenEntity addClientToken(@NonNull EncryptedClientToken clientToken) {
        final var clientTokenEntity = new ClientTokenEntity(this,clientToken);
        this.getClientTokens().add(clientTokenEntity);
        return clientTokenEntity;
    }

    public void removeClientToken(@NonNull UUID tokenId) {
        this.getClientTokens().removeIf(p -> tokenId.equals(p.getUuid()));
    }


    public static @NonNull Function2<? super String, ? super String, ? extends ClientEntity> getConstructor(@NonNull Platform platform) {
        return switch (platform) {
            case DISCORD ->  DiscordClientEntity::new;
            case TWITCH ->  TwitchClientEntity::new;
            default -> throw new IllegalArgumentException("Invalid platform: Cannot create a client for the platform '"+platform+"'");
        };
    }

    public <E extends ClientEntity> @NonNull E toSpecificPlatform(@NonNull Class<E> clientType) {
        if (clientType.isInstance(this)) {
            return clientType.cast(this);
        }

        throw new InvalidClientType(getUuid(), clientType);
    }

}
