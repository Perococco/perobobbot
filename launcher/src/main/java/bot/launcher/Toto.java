package bot.launcher;

import bot.blackjack.engine.Game;
import bot.common.lang.Printer;
import perococco.bot.blackjack.engine.action.AddPlayer;
import perococco.bot.blackjack.engine.action.DealOneCard;

import java.util.concurrent.ExecutionException;

public class Toto {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(Printer printer = Printer.toFile("/home/perococco/test.txt")) {
            final Game game = Game.builder().tableSize(2).build();

            game.mutate(AddPlayer.with("Marc",100));
            game.mutate(AddPlayer.with("Jean",13));
            game.mutate(DealOneCard.create());
            game.mutate(DealOneCard.create());
            game.mutate(DealOneCard.create());
            game.mutate(DealOneCard.create());
            game.mutate(DealOneCard.create());
            game.mutate(DealOneCard.create());

            game.withTable(t -> t.dump(printer)).toCompletableFuture().get();
        }
    }

}
