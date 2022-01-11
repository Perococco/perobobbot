package perobobbot.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.lang.Platform;
import perobobbot.lang.token.ClientToken;
import perobobbot.lang.token.EncryptedClientToken;
import perobobbot.lang.token.EncryptedClientTokenView;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT_TOKEN")
@NoArgsConstructor
@Getter @Setter
public class ClientTokenEntity extends TokenEntityBase {


    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private ClientEntity client;

    public ClientTokenEntity(@NonNull ClientEntity client,
                                 @NonNull ClientToken<String> token) {
        super(token);
        this.client = client;
    }

    @Override
    public @NonNull Platform getPlatform() {
        return client.getPlatform();
    }

    public @NonNull EncryptedClientToken toClientToken() {
        return EncryptedClientToken.builder()
                                   .accessToken(getAccessToken())
                                   .duration(getDuration())
                                   .expirationInstant(getExpirationInstant())
                                   .build();
    }

    public @NonNull EncryptedClientTokenView toView() {
        return new EncryptedClientTokenView(getUuid(),client.toView().stripSecret(),toClientToken());
    }
}
