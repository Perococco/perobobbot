open module bot.common.irc.test {
    requires static lombok;
    requires java.desktop;

    requires bot.common.irc;

    requires org.junit.jupiter.api;
    requires org.junit.jupiter.params;

    exports bot.common.irc.test;
}