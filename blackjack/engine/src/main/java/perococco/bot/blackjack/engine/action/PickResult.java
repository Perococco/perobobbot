package perococco.bot.blackjack.engine.action;

import bot.blackjack.engine.Card;
import bot.blackjack.engine.Deck;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PickResult {

    @NonNull
    private final Deck deck;

    @NonNull
    private final ImmutableList<Card> pickedCards;
}
