package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.data.domain.ClientTokenEntity;
import perobobbot.lang.Client;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
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

    @Column(name = "PLATFORM")
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

    public @NonNull Client toView() {
        return Client.builder()
                     .id(getUuid())
                     .platform(platform)
                     .clientId(clientId)
                     .clientSecret(Secret.with(this.getClientSecret()))
                     .build();
    }
}
