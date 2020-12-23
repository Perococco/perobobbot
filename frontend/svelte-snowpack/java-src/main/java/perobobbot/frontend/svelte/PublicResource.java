package perobobbot.frontend.svelte;

import java.net.URL;

public class PublicResource {

    public static URL publicURL() {
        return PublicResource.class.getResource("public");
    }
}
