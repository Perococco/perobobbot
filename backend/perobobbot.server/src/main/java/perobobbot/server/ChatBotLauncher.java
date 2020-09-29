package perobobbot.server;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.program.core.ProgramExecutor;
import perobobbot.twitch.chat.TwitchChatIO;

@Log4j2
public class ChatBotLauncher {

    private ProgramExecutor programExecutor;

    private void launchBot(@NonNull TwitchChatIO chat) {
//        programExecutor = ProgramExecutor.create();
//
//        final PrivMsgFromTwitchListener listener = PrivMsgFromTwitchListener.with(
//                msg -> new TwitchExecutionContext(chat, msg),
//                programExecutor::handleMessage
//        );
//
//        chat.addPrivateMessageListener(listener);
    }

//    @Override
//    public void destroy() throws Exception {
//        Optional.ofNullable(programExecutor).ifPresent(ProgramExecutor::stop);
//    }

//    @Override
//    public void run(ApplicationArguments args) {
//        try {
//            final Channel perococco = Channel.create("perococco");
//            final TwitchChatOptions options = createTwitchChatOptions(perococco);
//            final TwitchChat twitchChat = TwitchChat.create(options);
//
//            twitchChat.start()
//                      .thenAccept(this::launchBot)
//                      .whenComplete((v, t) -> {
//                          if (t != null) {
//                              t.printStackTrace();
//                              LOG.error("Fail to launch ChatBot", t);
//                          }
//                      });
//        } catch (Throwable t) {
//            ThrowableTool.interruptThreadIfCausedByInterruption(t);
//            LOG.error("Fail to launch ChatBot", t);
//        }
//    }

//
//    @NonNull
//    private TwitchChatOptions createTwitchChatOptions(@NonNull Channel channel) throws IOException {
//        final Secret secret = new Secret(Files.readString(Path.of("/home/perococco/twitch_keys/perobobbot.txt")));
//        return TwitchChatOptions.builder()
//                                .channel(channel)
//                                .nick("perobobbot")
//                                .secret(secret)
//                                .build();
//    }
//
}
