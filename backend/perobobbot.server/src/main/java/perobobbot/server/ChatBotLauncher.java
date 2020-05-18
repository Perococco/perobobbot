package perobobbot.server;

import perobobbot.common.lang.Secret;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.program.core.ProgramExecutor;
import perobobbot.program.sample.Samples;
import lombok.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import perobobbot.twitch.chat.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class ChatBotLauncher implements ApplicationRunner {


    private void launchBot(@NonNull TwitchChatIO chat) {

        final ProgramExecutor programExecutor = ProgramExecutor.create();
        programExecutor.registerProgram(Samples.ECHO);
        programExecutor.registerProgram(Samples.PING);

        chat.addPrivateMessageListener(TwitchExecutionContext.toListener(chat, programExecutor::handleMessage));
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            final Channel perococco = Channel.create("perococco");
            final TwitchChatOptions options = createTwitchChatOptions(perococco);
            final TwitchChat twitchChat = TwitchChat.create(options);

            twitchChat.start()
                      .thenAccept(this::launchBot);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            t.printStackTrace();
        }
    }


    private TwitchChatOptions createTwitchChatOptions(@NonNull Channel channel) throws IOException {
        final Secret secret = new Secret(Files.readString(Path.of("/home/perococco/twitch_keys/perobobbot.txt")));
        return TwitchChatOptions.builder()
                                .channel(channel)
                                .nick("perobobbot")
                                .secret(secret)
                                .build();
    }

}