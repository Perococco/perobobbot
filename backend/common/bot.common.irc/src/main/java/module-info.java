import bot.common.irc.IRCParserFactory;
import perobobbot.bot.common.irc.PerococcoIRCParserFactory;

/**
 * @author perococco
 **/
module perobobbot.common.irc {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;

    requires org.apache.logging.log4j;

    requires perobobbot.common.lang;

    exports bot.common.irc;
    exports perobobbot.bot.common.irc;

    uses IRCParserFactory;
    provides IRCParserFactory with PerococcoIRCParserFactory;
}
