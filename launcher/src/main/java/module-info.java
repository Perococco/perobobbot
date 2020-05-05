module bot.launcher {
    requires static lombok;

    requires java.desktop;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;

    requires com.google.common;

    requires bot.twitch.chat;
    requires bot.common.lang;
    requires bot.chat.advanced;
    requires bot.program.core;
    requires bot.program.sample;

    opens bot.launcher to spring.core;

    exports bot.launcher;
}