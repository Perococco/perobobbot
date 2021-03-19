package perobobbot.rest.client;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.data.com.Extension;

import java.util.concurrent.CompletionStage;

public interface ExtensionClient {

    @NonNull CompletionStage<ImmutableList<Extension>> listExtensions();
}
