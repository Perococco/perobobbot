package bot.common.irc.test;

import bot.common.irc.IRCParsing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import perobobbot.bot.common.irc.PerococcoIRCParser;

/**
 * @author perococco
 **/
public class IRCParserTest {

    private IRCParsing ircParsing;

    @BeforeEach
    void setUp() {
        ircParsing = new PerococcoIRCParser().parse(":perococco!perococco@perococco.tmi.twitch.tv JOIN #joueur_du_grenier");
    }

    @Test
    public void shouldHaveAPrefix() {
        Assertions.assertTrue(ircParsing.prefix().isPresent());
    }

    @Test
    public void shouldHaveUserPrefix() {
        Assertions.assertTrue(ircParsing.prefix().get().user().isPresent());
    }

    @Test
    public void shouldHaveHostPrefix() {
        Assertions.assertTrue(ircParsing.prefix().get().host().isPresent());
    }

    @Test
    public void shouldHaveNickPerococco() {
        Assertions.assertEquals("perococco",ircParsing.prefix().get().getNickOrServerName());
    }

    @Test
    public void shouldHaveUserPerococco() {
        Assertions.assertEquals("perococco",ircParsing.prefix().get().user().get());
    }

    @Test
    public void shouldHaveHostPerococcoTmiTwitchTv() {
        Assertions.assertEquals("perococco.tmi.twitch.tv",ircParsing.prefix().get().host().get());
    }


}
