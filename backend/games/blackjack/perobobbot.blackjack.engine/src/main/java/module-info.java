module perobobbot.blackjack.engine {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.common.lang;

    requires com.google.common;

    exports perbobbot.blackjack.engine;
    exports perbobbot.blackjack.engine.exception;
    exports perbobbot.blackjack.engine.action;
}