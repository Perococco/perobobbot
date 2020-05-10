package perococco.bot.chat.advanced;

import bot.chat.advanced.Command;
import bot.chat.advanced.DispatchSlip;
import bot.chat.advanced.ReceiptSlip;
import bot.chat.advanced.Request;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
public class BasicDispatchSlip implements DispatchSlip {

    @NonNull
    private final Instant dispatchingTime;

    @NonNull
    private final Command sentCommand;

}
