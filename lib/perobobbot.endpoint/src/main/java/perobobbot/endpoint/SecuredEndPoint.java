
package perobobbot.endpoint;

import lombok.NonNull;
import perobobbot.security.com.SimpleUser;

public interface SecuredEndPoint<I> {

    @NonNull EndPoint<I> createEndPoint(@NonNull SimpleUser user);

}

