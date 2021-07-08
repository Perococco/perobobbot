package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.token.EncryptedUserToken;

import java.util.UUID;

@Value
public class EncryptedTokenInfo {
    @NonNull UUID tokenId;
    @NonNull EncryptedUserToken decryptedUserToken;

    public DecryptedTokenInfo decrypt(@NonNull TextEncryptor textEncryptor) {
        return new DecryptedTokenInfo(tokenId,decryptedUserToken.decrypt(textEncryptor));
    }
}
