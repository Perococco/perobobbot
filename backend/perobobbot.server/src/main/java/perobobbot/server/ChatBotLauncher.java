package perobobbot.server;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import perobobbot.common.lang.Secret;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.common.lang.fp.Function1;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ProgramExecutor;
import perobobbot.program.sample.Samples;
import perobobbot.twitch.chat.*;
import perobobbot.twitch.chat.event.ReceivedMessage;
import perobobbot.twitch.chat.message.from.PrivMsgFromTwitch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Log4j2
public class ChatBotLauncher implements ApplicationRunner {


    private void launchBot(@NonNull TwitchChatIO chat) {

        final ProgramExecutor programExecutor = ProgramExecutor.create();
        programExecutor.registerProgram(Samples.ECHO);
        programExecutor.registerProgram(Samples.PING);

        final PrivMsgFromTwitchListener listener = PrivMsgFromTwitchListener.with(
                msg -> new TwitchExecutionContext(chat,msg),
                programExecutor::handleMessage
        );

        chat.addPrivateMessageListener(listener);
    }


    @Override
    public void run(ApplicationArguments args) {
        try {
            final Channel perococco = Channel.create("perococco");
            final TwitchChatOptions options = createTwitchChatOptions(perococco);
            final TwitchChat twitchChat = TwitchChat.create(options);

            twitchChat.start()
                      .thenAccept(this::launchBot);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.error("Fail to launch ChatBot",t);
        }
    }


    @NonNull
    private TwitchChatOptions createTwitchChatOptions(@NonNull Channel channel) throws IOException {
        final Secret secret = new Secret(Files.readString(Path.of("/home/perococco/twitch_keys/perobobbot.txt")));
        return TwitchChatOptions.builder()
                                .channel(channel)
                                .nick("perobobbot")
                                .secret(secret)
                                .build();
    }

}
