package perococco.bot.twitch.program;

import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import bot.twitch.program.ProgramCommand;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class CommandExtraction {

    @NonNull
    private final String name;

    @NonNull
    private final ImmutableList<String> parameters;

    @NonNull
    public ProgramCommand toProgramCommandWithReception(@NonNull ReceivedMessage<PrivMsgFromTwitch> reception) {
        return ProgramCommand.builder()
                .state(reception.state())
                .receptionTime(reception.receptionTime())
                .message(reception.message())
                .commandName(name)
                .parameters(parameters)
                .build();
    }
}
