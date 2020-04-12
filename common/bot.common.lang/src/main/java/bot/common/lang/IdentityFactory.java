package bot.common.lang;

import lombok.NonNull;

import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public interface IdentityFactory extends Prioritized {

    @NonNull
    <S> Identity<S> create(@NonNull S initialValue);

    @NonNull
    static IdentityFactory getInstance() {
        return ServiceLoaderHelper.getService(ServiceLoader.load(IdentityFactory.class));
    }
}
