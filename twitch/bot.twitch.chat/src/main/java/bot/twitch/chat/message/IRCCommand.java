package bot.twitch.chat.message;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public enum IRCCommand {
    CAP,
    CLEARCHAT,
    CLEARMSG,
    JOIN,
    HOSTTARGET,
    MODE,
    NICK,
    NOTICE,
    PART,
    PASS,
    PING,
    PRIVMSG,
    RECONNECT,
    ROOMSTATE,
    RPL_WELCOME("001"),
    RPL_YOURHOST("002"),
    RPL_CREATED("003"),
    RPL_MYINFO("004"),
    RPL_NAMREPLY("353"),
    RPL_ENDOFNAMES("366"),
    RPL_MOTD("372"),
    RPL_MOTDSTART("375"),
    RPL_ENDOFMOTD("376"),
    USERNOTICE,
    USERSTATE,
    ERR_UNKNOWNCOMMAND("421"),
    ;

    @NonNull
    private final String numericAlias;

    IRCCommand() {
        numericAlias="";
    }

}
