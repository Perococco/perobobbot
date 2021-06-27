package perobobbot.lang.chain;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChainExecutor<P,R> {

    private final @NonNull ImmutableList<Link<P,R>> links;

    public R call(@NonNull P parameter) {
        if (links.isEmpty()) {
            throw new IllegalStateException("No link in the chain");
        }
        return new SimpleChain(0).callNext(parameter);
    }

    @RequiredArgsConstructor
    private class SimpleChain implements Chain<P,R> {

        private final int index;

        @Override
        public @NonNull R callNext(@NonNull P parameter) {
            if (index<links.size()) {
                return links.get(index).call(parameter,new SimpleChain(index+1));
            }
            throw new IllegalStateException("no more link in the chain");
        }
    }
}
