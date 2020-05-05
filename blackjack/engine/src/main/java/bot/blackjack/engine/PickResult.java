package bot.blackjack.engine;

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
