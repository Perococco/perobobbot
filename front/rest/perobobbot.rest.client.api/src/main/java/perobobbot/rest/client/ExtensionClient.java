package perobobbot.rest.client;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.data.com.Extension;

public interface ExtensionClient {

    @NonNull ImmutableList<Extension> listExtensions();
}
