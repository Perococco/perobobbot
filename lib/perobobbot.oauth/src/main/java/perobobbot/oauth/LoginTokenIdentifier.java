package perobobbot.oauth;

import lombok.NonNull;
import lombok.Value;

@Value
public class LoginTokenIdentifier implements TokenIdentifier{

    @NonNull String login;

    @Override
    public <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
