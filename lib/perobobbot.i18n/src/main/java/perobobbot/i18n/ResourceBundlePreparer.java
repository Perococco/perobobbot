package perobobbot.i18n;

import lombok.NonNull;

import java.util.ResourceBundle;

public interface ResourceBundlePreparer {

    @NonNull
    static ResourceBundlePreparer nop() {
        return r -> r;
    }

    @NonNull
    ResourceBundle prepare(@NonNull ResourceBundle resourceBundle);
}
