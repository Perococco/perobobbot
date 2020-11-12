import perobobbot.irc.IRCParserFactory;
import perococco.perobobbot.irc.PerococcoIRCParserFactory;

/**
 * @author perococco
 **/
module perobobbot.irc {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;

    requires org.apache.logging.log4j;

    requires perobobbot.lang;

    exports perobobbot.irc;

    uses IRCParserFactory;
    provides IRCParserFactory with PerococcoIRCParserFactory;
}
