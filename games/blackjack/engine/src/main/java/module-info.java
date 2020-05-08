module bot.blackjack.engine {
    requires static lombok;
    requires java.desktop;

    requires bot.common.lang;

    requires com.google.common;

    exports bot.blackjack.engine;
    exports bot.blackjack.engine.exception;
    exports perococco.bot.blackjack.engine.action;
}