package perobobbot.localio;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.DispatchSlip;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.fp.Function1;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class LocalIO implements LocalChat {

    private final @NonNull ChatConnectionInfo chatConnectionInfo;

    private final @NonNull LocalSender localSender;

    @Override
    public @NonNull CompletionStage<? extends DispatchSlip> send(@NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        return localSender.send(chatConnectionInfo,messageBuilder);
    }

}
