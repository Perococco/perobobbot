import perobobbot.common.irc.IRCParserFactory;
import perococco.perobobbot.common.irc.PerococcoIRCParserFactory;

/**
 * @author perococco
 **/
module perobobbot.common.irc {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;

    requires org.apache.logging.log4j;

    requires perobobbot.lang;

    exports perobobbot.common.irc;

    uses IRCParserFactory;
    provides IRCParserFactory with PerococcoIRCParserFactory;
}
