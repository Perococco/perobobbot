package bot.twitch.program;


import bot.twitch.chat.TwitchChatIO;
import lombok.NonNull;
import perococco.bot.twitch.program.PerococcoChatProgramManager;

public interface ChatProgramManager {

    static ChatProgramManager create(@NonNull TwitchChatIO twitchChatIO) {
        return new PerococcoChatProgramManager(twitchChatIO,"@","!");
    }

    @NonNull
    ChatProgramManager registerChatProgram(@NonNull ChatProgram chatProgram);


    void start();

    void stop();

}
