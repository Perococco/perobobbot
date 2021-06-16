package perobobbot.lang.chain;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChainExecutor<P> {

    private final @NonNull ImmutableList<Link<P>> links;

    public void call(@NonNull P parameter) {
        if (links.isEmpty()) {
            return;
        }
        final var firstLink = links.get(0);
        firstLink.call(parameter,new SimpleChain(0));

    }

    @RequiredArgsConstructor
    private class SimpleChain implements Chain<P> {

        private final int index;

        @Override
        public void callNext(@NonNull P parameter) {
            final int nextIndex = index+1;
            if (nextIndex < links.size()) {
                final var link = links.get(nextIndex);
                link.call(parameter,new SimpleChain(nextIndex));
            }
        }
    }
}
