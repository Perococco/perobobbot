package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.data.domain.ClientEntity;
import perobobbot.lang.Platform;
import perobobbot.lang.token.ClientToken;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
public class ClientTokenEntityBase extends TokenEntityBase {

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private ClientEntity client;

    public ClientTokenEntityBase(@NonNull ClientEntity client,
                                 @NonNull ClientToken<String> token) {
        super(token);
        this.client = client;
    }

    @Override
    public @NonNull Platform getPlatform() {
        return client.getPlatform();
    }
}
