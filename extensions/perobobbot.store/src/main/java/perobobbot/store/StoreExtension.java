package perobobbot.store;

import lombok.NonNull;
import perobobbot.chat.core.IO;
import perobobbot.extension.ExtensionBase;
import perobobbot.extension.StoreController;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.ChatUser;
import perobobbot.lang.Platform;
import perobobbot.lang.PointType;

public class StoreExtension extends ExtensionBase {

    public static final String NAME = "Store";

    private final @NonNull IO io;

    private final @NonNull StoreController storeController;

    public StoreExtension(@NonNull IO io, @NonNull StoreController storeController) {
        super(NAME);
        this.io = io;
        this.storeController = storeController;
    }

    public void showMyPoint(@NonNull ChatConnectionInfo chatConnectionInfo,
                            @NonNull ChatUser chatUser,
                            @NonNull String channelName,
                            @NonNull PointType pointType) {
        final var balance = storeController.getBalance(chatUser,channelName,pointType);
        io.send(chatConnectionInfo,channelName,chatUser.getHighlightedUserName()+" tu as "+balance.getAmount()+" points.");
    }

    public void addSomePoint(@NonNull String userId, @NonNull Platform platform, @NonNull String channelName, @NonNull PointType type, int amount) {
        storeController.addToBalance(userId,platform,channelName,type,amount);
    }
}
