package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.ClientTokenEntityBase;
import perobobbot.lang.token.ClientToken;
import perobobbot.lang.token.EncryptedClientToken;
import perobobbot.lang.token.EncryptedClientTokenView;
import perobobbot.lang.token.EncryptedUserToken;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT_TOKEN")
@NoArgsConstructor
public class ClientTokenEntity extends ClientTokenEntityBase {

    public ClientTokenEntity(@NonNull ClientEntity client,
                             @NonNull ClientToken<String> encryptedToken) {
        super(client, encryptedToken);
    }

    public @NonNull EncryptedClientToken toClientToken() {
        return EncryptedClientToken.builder()
                                   .accessToken(getAccessToken())
                                   .duration(getDuration())
                                   .expirationInstant(getExpirationInstant())
                                   .build();
    }

    public @NonNull EncryptedClientTokenView toView() {
        return new EncryptedClientTokenView(getUuid(),getClient().getClientId(),toClientToken());
    }
}
