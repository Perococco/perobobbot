package perobobbot.localio;

import lombok.NonNull;
import perobobbot.chat.core.DispatchSlip;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.fp.Function1;

import java.util.concurrent.CompletionStage;

public interface LocalSender {

    @NonNull CompletionStage<? extends DispatchSlip> send(@NonNull ChatConnectionInfo chatConnectionInfo, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);
}
