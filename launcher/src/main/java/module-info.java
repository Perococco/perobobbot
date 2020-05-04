module bot.launcher {
    requires static lombok;
    requires java.desktop;
    requires spring.context;
    requires spring.boot;
    requires bot.twitch.chat;
    requires bot.common.lang;
    requires spring.boot.autoconfigure;


    opens bot.launcher to spring.core;

    exports bot.launcher;
}