import bot.common.irc.IRCParserFactory;
import perobobbot.bot.common.irc.PerococcoIRCParserFactory;

/**
 * @author perococco
 **/
module bot.common.irc {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;

    requires org.apache.logging.log4j;

    requires bot.common.lang;

    exports bot.common.irc;

    uses IRCParserFactory;
    provides IRCParserFactory with PerococcoIRCParserFactory;
}
