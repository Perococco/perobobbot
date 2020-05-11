package perococco.perobobbot.common.lang;

import perobobbot.common.lang.*;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * @author perococco
 **/
@Log4j2
@Priority(Integer.MIN_VALUE)
public class PerococcoAsyncIdentityFactory extends AsyncIdentityFactory {

    PerococcoAsyncIdentityFactory() {
    }

    @Override
    public @NonNull <S> AsyncIdentity<S> create(@NonNull S initialValue) {
        final ProxyAsyncIdentity<S> weakIdentity;
        {
            final PerococcoAsyncIdentity<S> identity = new PerococcoAsyncIdentity<>(initialValue);
            identity.start();
            weakIdentity = new ProxyAsyncIdentity<>(identity);
            Holder.DISPOSER.add(weakIdentity, identity::stop);
        }
        return weakIdentity;

    }

    private static class Holder {

        private static final Disposer DISPOSER = new Disposer();
    }


}
