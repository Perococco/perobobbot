package perococco.bot.common.lang;

import bot.common.lang.*;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * @author perococco
 **/
@Log4j2
@Priority(Integer.MIN_VALUE)
public class PerococcoIdentityFactory extends IdentityFactory {

    PerococcoIdentityFactory() {
    }

    @Override
    public @NonNull <S> Identity<S> create(@NonNull S initialValue) {
        final ProxyIdentity<S> weakIdentity;
        {
            final PerococcoIdentity<S> identity = new PerococcoIdentity<>(initialValue);
            identity.start();
            weakIdentity = new ProxyIdentity<>(identity);
            Holder.DISPOSER.add(weakIdentity, identity::stop);
        }
        return weakIdentity;

    }

    private static class Holder {

        private static final Disposer DISPOSER = new Disposer();
    }


}
