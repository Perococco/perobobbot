package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Type;
import perobobbot.data.domain.ClientTokenEntity;
import perobobbot.lang.DecryptedClient;
import perobobbot.lang.EncryptedClient;
import perobobbot.lang.Platform;
import perobobbot.lang.TextEncryptor;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class ClientEntityBase extends PersistentObjectWithUUID {

    @Column(name = "PLATFORM", unique = true)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private Platform platform;

    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "CLIENT_SECRET")
    private String clientSecret;

    @OneToMany(mappedBy = "client")
    private List<ClientTokenEntity> clientTokens = new ArrayList<>();

    public ClientEntityBase(Platform platform, String clientId, String clientSecret) {
        super(UUID.randomUUID());
        this.platform = platform;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public @NonNull EncryptedClient toView() {
        return EncryptedClient.builder()
                     .id(getUuid())
                     .platform(platform)
                     .clientId(clientId)
                     .clientSecret(this.getClientSecret())
                     .build();
    }

    public @NonNull DecryptedClient toDecryptedView(@NonNull TextEncryptor textEncryptor) {
        return toView().decrypt(textEncryptor);
    }

}
