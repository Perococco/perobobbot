package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.lang.Platform;
import perobobbot.lang.token.Token;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
public abstract class TokenEntityBase extends PersistentObjectWithUUID {

    @Column(name = "ACCESS_TOKEN", nullable = false)
    @NotBlank
    private String accessToken;

    @Column(name = "DURATION",nullable = false)
    private long duration;

    @Column(name = "EXPIRATION_INSTANT",nullable = false)
    private Instant expirationInstant;

    protected TokenEntityBase(@NonNull Token<String> encryptedToken) {
        super(UUID.randomUUID());
        this.accessToken = encryptedToken.getAccessToken();
        this.duration = encryptedToken.getDuration();
        this.expirationInstant = encryptedToken.getExpirationInstant();
    }

    public abstract @NonNull Platform getPlatform();

}
