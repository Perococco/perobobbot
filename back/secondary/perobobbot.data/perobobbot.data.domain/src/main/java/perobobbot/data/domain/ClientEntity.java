package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.ClientEntityBase;
import perobobbot.lang.token.EncryptedClientToken;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

@Entity
@Table(name = "CLIENT")
@NoArgsConstructor
public class ClientEntity extends ClientEntityBase {

    public @NonNull ClientTokenEntity addClientToken(@NonNull EncryptedClientToken clientToken) {
        final var clientTokenEntity = new ClientTokenEntity(this,clientToken);
        this.getClientTokens().add(clientTokenEntity);
        return clientTokenEntity;
    }

    public void removeClientToken(@NonNull UUID tokenId) {
        this.getClientTokens().removeIf(p -> tokenId.equals(p.getUuid()));
    }
}
