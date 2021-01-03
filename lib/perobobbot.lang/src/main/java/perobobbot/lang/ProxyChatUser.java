package perobobbot.lang;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class ProxyChatUser implements ChatUser {

    @Delegate
    private final ChatUser delegate;
}
