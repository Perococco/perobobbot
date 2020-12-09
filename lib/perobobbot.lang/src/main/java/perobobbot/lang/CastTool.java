package perobobbot.lang;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import perobobbot.lang.fp.Function1;

import java.awt.*;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author perococco
 **/
@Log4j2
public class CastTool {

    private static final Marker CAST_MARKER = MarkerManager.getMarker("CAST_ERROR");

    @NonNull
    public static <A> Function1<Object,Optional<A>> caster(@NonNull Class<A> type) {
        return o -> cast(type,o);
    }

    public static @NonNull Optional<Color> castToColor(@NonNull String colorAsString) {
        return Optional.of(Color.decode(colorAsString));
    }

    /**
     * @param type class of the type to cast the object to
     * @param object the object to cast
     * @param <A> the type to cast the object to
     * @return an optional containing the casted object if it could be casted, an empty optional otherwise
     */
    @NonNull
    public static <A> Optional<A> cast(@NonNull Class<A> type, @NonNull Object object) {
        if (type.isInstance(object)) {
            return Optional.of(type.cast(object));
        }
        return Optional.empty();
    }

    /**
     * Try to convert a string to an integer
     * @param string the string to convert
     * @return an optional contain the conversion result, an empty optional if the conversion could not be done
     */
    @NonNull
    public static Optional<Integer> castToInt(@NonNull String string) {
        return castToNumber(string,Integer::parseInt);
    }

    public static int castToInt(@NonNull String string, int defaultValue) {
        return castToInt(string).orElse(defaultValue);
    }

    @NonNull
    public static Optional<Double> castToDouble(@NonNull String string) {
        return castToNumber(string,Double::parseDouble);
    }

    public static double castToDouble(@NonNull String string, double defaultValue) {
        return castToDouble(string).orElse(defaultValue);
    }

    @NonNull
    public static Optional<Long> castToLong(@NonNull String string) {
        return castToNumber(string,Long::parseLong);
    }

    public static double castToLong(@NonNull String string, long defaultValue) {
        return castToLong(string).orElse(defaultValue);
    }

    @NonNull
    public static <T,R> Optional<R> castAndCall(@NonNull Object object, @NonNull Class<T> type, @NonNull Function<? super T, ? extends Optional<R>> mapper) {
        return cast(type,object).flatMap(mapper);
    }

    private static <N extends Number> Optional<N> castToNumber(@NonNull String string, @NonNull Function1<? super String, ? extends N> caster) {
        try {
            return Optional.of(caster.apply(string));
        } catch (NumberFormatException nfe) {
            LOG.warn(CAST_MARKER,() -> String.format("Fail to cast '%s' into number",string));
            return Optional.empty();
        }
    }

}
