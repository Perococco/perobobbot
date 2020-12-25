package perobobbot.extension;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(of = {"extensionName"})
public class ExtensionInfo implements Comparable<ExtensionInfo> {

    @NonNull String extensionName;

    boolean enabled;

    @Override
    public int compareTo(ExtensionInfo o) {
        return this.extensionName.compareTo(o.extensionName);
    }
}
