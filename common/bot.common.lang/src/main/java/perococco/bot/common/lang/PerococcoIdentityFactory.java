package perococco.bot.common.lang;

import bot.common.lang.Disposer;
import bot.common.lang.Identity;
import bot.common.lang.IdentityFactory;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * @author perococco
 **/
@Log4j2
public class PerococcoIdentityFactory extends IdentityFactory {

    @Getter
    private final int priority = Integer.MIN_VALUE;

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
