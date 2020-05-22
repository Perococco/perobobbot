package perococco.perobobbot.blackjack.engine;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perbobbot.blackjack.engine.Hand;
import perbobbot.blackjack.engine.Player;
import perbobbot.blackjack.engine.TableState;

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

        return switch (this.currentState) {
            case OPEN_TO_NEW_PLAYER -> nextPlayers.size() >= tableSize ? TableState.DEALING : TableState.OPEN_TO_NEW_PLAYER;
            case DEALING -> nextDealerHand.hasLessThanTwoCards() ? TableState.DEALING : TableState.PLAYER_PHASE;
            case PLAYER_PHASE -> allPlayerDone ? (dealerDone ? TableState.GAME_OVER : TableState.DEALER_PHASE) : TableState.PLAYER_PHASE;
            case DEALER_PHASE -> dealerDone ? TableState.GAME_OVER : TableState.DEALER_PHASE;
            default -> currentState;
        };
    }
}
