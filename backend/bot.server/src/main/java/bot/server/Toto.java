package bot.server;

import bot.blackjack.engine.Card;
import bot.blackjack.engine.Deck;
import bot.blackjack.engine.Game;
import bot.common.lang.Printer;

import java.util.concurrent.ExecutionException;

public class Toto {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(Printer printer = Printer.toFile("/home/perococco/blackjack.txt")) {
            final Game game = new Game(Deck.with(
                    Card.ACE_OF_CLUBS,Card.KING_OF_DIAMONDS,
                    Card.THREE_OF_CLUBS, Card.THREE_OF_HEARTS,
                    Card.ACE_OF_CLUBS, Card.SEVEN_OF_CLUBS,

                    Card.FOUR_OF_CLUBS,Card.ACE_OF_CLUBS,
                    Card.FIVE_OF_DIAMONDS, Card.EIGHT_OF_CLUBS,
                    Card.ACE_OF_CLUBS,Card.TWO_OF_CLUBS
            ),2);



            game.addPlayer("Marc",100);
            game.addPlayer("Jean",13);
            game.dealAllHands().toCompletableFuture().get();


            game.stand("Marc");
            game.split("Jean");
            game.hit("Jean");
            game.hit("Jean");
            game.stand("Jean");
            game.doubleHand("Jean")
                .thenAccept(t -> t.dump(printer))
                .toCompletableFuture().get();
            ;
        }
    }

}
