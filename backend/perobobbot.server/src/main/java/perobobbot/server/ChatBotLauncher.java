package perobobbot.server;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import perobobbot.common.lang.Secret;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.program.core.ProgramExecutor;
import perobobbot.program.sample.Samples;
import perobobbot.twitch.chat.*;
import perobobbot.twitch.chat.program.TwitchExecutionContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Component
@Log4j2
public class ChatBotLauncher implements ApplicationRunner, DisposableBean {

    private ProgramExecutor programExecutor;

    private void launchBot(@NonNull TwitchChatIO chat) {
        programExecutor = ProgramExecutor.create();
        programExecutor.registerProgram(Samples.SAY_HELLO);
        programExecutor.registerProgram(Samples.ECHO);
        programExecutor.registerProgram(Samples.PING);

        final PrivMsgFromTwitchListener listener = PrivMsgFromTwitchListener.with(
                msg -> new TwitchExecutionContext(chat, msg),
                programExecutor::handleMessage
        );

        chat.addPrivateMessageListener(listener);
    }

    @Override
    public void destroy() throws Exception {
        Optional.ofNullable(programExecutor).ifPresent(ProgramExecutor::stop);
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            final Channel perococco = Channel.create("perococco");
            final TwitchChatOptions options = createTwitchChatOptions(perococco);
            final TwitchChat twitchChat = TwitchChat.create(options);

            twitchChat.start()
                      .thenAccept(this::launchBot)
                      .whenComplete((v, t) -> {
                          if (t != null) {
                              t.printStackTrace();
                              LOG.error("Fail to launch ChatBot", t);
                          }
                      });
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.error("Fail to launch ChatBot", t);
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
