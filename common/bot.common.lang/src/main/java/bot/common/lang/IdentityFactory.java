package bot.common.lang;

import lombok.NonNull;

import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public abstract class IdentityFactory {

    @NonNull
    public abstract <S> Identity<S> create(@NonNull S initialValue);


    @NonNull
    public static IdentityFactory getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final IdentityFactory INSTANCE = ServiceLoaderHelper.getService(ServiceLoader.load(IdentityFactory.class));
    }

}
