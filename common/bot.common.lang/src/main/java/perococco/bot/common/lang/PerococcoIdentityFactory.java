package perococco.bot.common.lang;

import bot.common.lang.Disposer;
import bot.common.lang.Identity;
import bot.common.lang.IdentityFactory;
import bot.common.lang.Priority;
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
        final PerococcoIdentity<S> identity = new PerococcoIdentity<>(initialValue);
        identity.start();
        return Holder.DISPOSER.add(identity, PerococcoIdentity::stop);
    }

    private static class Holder {

        private static final Disposer DISPOSER = new Disposer();
    }


}
