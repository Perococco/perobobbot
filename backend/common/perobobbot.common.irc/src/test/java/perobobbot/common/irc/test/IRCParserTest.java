package perobobbot.common.irc.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import perobobbot.common.irc.IRCParsing;
import perococco.perobobbot.common.irc.PerococcoIRCParser;

/**
 * @author perococco
 **/
public class IRCParserTest {

    private IRCParsing ircParsing;

    @BeforeEach
    public void setUp() {
        ircParsing = new PerococcoIRCParser().parse(":perococco!perococco@perococco.tmi.twitch.tv JOIN #joueur_du_grenier");
    }

    @Test
    public void shouldHaveAPrefix() {
        Assertions.assertTrue(ircParsing.getPrefix().isPresent());
    }

    @Test
    public void shouldHaveUserPrefix() {
        Assertions.assertTrue(ircParsing.getPrefix().get().user().isPresent());
    }

    @Test
    public void shouldHaveHostPrefix() {
        Assertions.assertTrue(ircParsing.getPrefix().get().host().isPresent());
    }

    @Test
    public void shouldHaveNickPerococco() {
        Assertions.assertEquals("perococco",ircParsing.getPrefix().get().getNickOrServerName());
    }

    @Test
    public void shouldHaveUserPerococco() {
        Assertions.assertEquals("perococco",ircParsing.getPrefix().get().user().get());
    }

    @Test
    public void shouldHaveHostPerococcoTmiTwitchTv() {
        Assertions.assertEquals("perococco.tmi.twitch.tv",ircParsing.getPrefix().get().host().get());
    }


}
