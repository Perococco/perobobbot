package bot.common.lang;

import lombok.NonNull;

import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public abstract class AsyncIdentityFactory {

    @NonNull
    public abstract <S> AsyncIdentity<S> create(@NonNull S initialValue);

    @NonNull
    public static AsyncIdentityFactory getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final AsyncIdentityFactory INSTANCE = ServiceLoaderHelper.getService(ServiceLoader.load(AsyncIdentityFactory.class));
    }

}
