package perobobbot.store;

import lombok.NonNull;
import perobobbot.chat.core.IO;
import perobobbot.extension.ExtensionBase;
import perobobbot.lang.*;

public class BankExtension extends ExtensionBase {

    public static final String MESSAGE = "%s tu as %d %s";

    public static final String NAME = "Bank";

    private final @NonNull IO io;

    private final @NonNull Bank bank;

    public BankExtension(@NonNull IO io, @NonNull Bank bank) {
        super(NAME);
        this.io = io;
        this.bank = bank;
    }

    public void showMyPoint(@NonNull ChatConnectionInfo chatConnectionInfo,
                            @NonNull ChatUser chatUser,
                            @NonNull String channelName,
                            @NonNull PointType pointType) {
        final var balance = bank.getBalance(UserOnChannel.from(chatUser,channelName), pointType);
        io.send(chatConnectionInfo,channelName,MESSAGE.formatted(chatUser.getHighlightedUserName(),
                                                                 balance.getAmount(),
                                                                 balance.getSafe().getType().name().toLowerCase()));
    }

    public void addSomePoint(@NonNull UserOnChannel userOnChannel, @NonNull PointType type, int amount) {
        bank.addToBalance(userOnChannel, type, amount);
    }
}
