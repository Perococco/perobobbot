package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.com.DataCredentialInfo;
import perobobbot.data.domain.base.TokenEntityBase;
import perobobbot.lang.Credential;
import perobobbot.lang.Platform;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "CREDENTIAL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenEntity extends TokenEntityBase {

    public TokenEntity(@NotBlank UserEntity owner, @NonNull Platform platform) {
        super(owner,platform);
    }

    public @NonNull DataCredentialInfo toView() {
        return DataCredentialInfo.with(this.uuid, this.getOwner().getLogin(), this.getPlatform(), this.getNick(), this.getSecret());
    }

    public @NonNull Credential getCredential() {
        return new Credential(getNick(),getSecret());
    }
}
