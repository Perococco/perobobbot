module bot.twitch.program {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;

    requires bot.twitch.chat;
    requires bot.common.lang;


    exports bot.twitch.program;
}