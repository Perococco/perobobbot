package bot.common.lang;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

/**
 * @author perococco
 **/
@Log4j2
public class CastTool {

    private static final Marker CAST_MARKER = MarkerManager.getMarker("CAST_ERROR");

    @NonNull
    public static <A> Function<Object,Optional<A>> caster(@NonNull Class<A> type) {
        return o -> cast(type,o);
    }

    @NonNull
    public static <A> Optional<A> cast(@NonNull Class<A> type, @NonNull Object object) {
        if (type.isInstance(object)) {
            return Optional.of(type.cast(object));
        }
        return Optional.empty();
    }

    @NonNull
    public static OptionalInt castToInt(@NonNull String string) {
        try {
            return OptionalInt.of(Integer.parseInt(string));
        } catch (NumberFormatException nfe) {
            LOG.warn(CAST_MARKER,() -> String.format("Fail to cast '%s' into int",string),nfe);
            return OptionalInt.empty();
        }
    }

    public static int castToInt(@NonNull String string, int defaultValue) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException nfe) {
            LOG.warn(CAST_MARKER,() -> String.format("Fail to cast '%s' into int",string),nfe);
            return defaultValue;
        }
    }

    @NonNull
    public static <T,R> Optional<R> castAndCheck(@NonNull Object object, @NonNull Class<T> type,  @NonNull Function<? super T, ? extends Optional<R>> mapper) {
        return cast(type,object).flatMap(mapper);
    }
}
