package perococco.bot.blackjack.engine;

import bot.blackjack.engine.Hand;
import bot.blackjack.engine.Player;
import bot.blackjack.engine.TableState;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;

public class NextStateEvaluator {

    @NonNull
    public static TableState evaluate(@NonNull TableState currentState,
                                      int tableSize,
                                      @NonNull ImmutableList<Player> nextPlayers,
                                      @NonNull Hand nextDealerHand) {
        return new NextStateEvaluator(currentState, tableSize, nextPlayers, nextDealerHand).evaluate();
    }

    private NextStateEvaluator(@NonNull TableState currentState,
                               int tableSize,
                               @NonNull ImmutableList<Player> nextPlayers,
                               @NonNull Hand nextDealerHand) {
        this.currentState = currentState;
        this.tableSize = tableSize;
        this.nextPlayers = nextPlayers;
        this.nextDealerHand = nextDealerHand;
    }

    private final TableState currentState;

    private final int tableSize;

    private final ImmutableList<Player> nextPlayers;

    private final Hand nextDealerHand;

    @NonNull
    private TableState evaluate() {
        final boolean allPlayerDone = nextPlayers.stream().allMatch(Player::isDone);
        final boolean dealerDone = nextDealerHand.isDone();

        switch (this.currentState) {
            case OPEN_TO_NEW_PLAYER: return nextPlayers.size() >= tableSize ? TableState.DEALING : TableState.OPEN_TO_NEW_PLAYER;
            case DEALING: return nextDealerHand.hasLessThanTwoCards() ? TableState.DEALING : TableState.PLAYER_PHASE;
            case PLAYER_PHASE: return allPlayerDone ? (dealerDone ? TableState.GAME_OVER : TableState.DEALER_PHASE) : TableState.PLAYER_PHASE;
            case DEALER_PHASE: return dealerDone ? TableState.GAME_OVER : TableState.DEALER_PHASE;
            default:
                return currentState;
        }
    }
}
