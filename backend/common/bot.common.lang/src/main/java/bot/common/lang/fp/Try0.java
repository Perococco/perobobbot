package bot.common.lang.fp;

import lombok.NonNull;

public interface Try0<R,X extends Throwable> {

    @NonNull
    R f() throws X;

}
