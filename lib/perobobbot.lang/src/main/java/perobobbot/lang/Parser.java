package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;
import perobobbot.lang.fp.TryResult;

public interface Parser<T> {

    @NonNull TryResult<ParsingFailure,T> parse(@NonNull String value);

    @NonNull Class<T> targetType();


    static @NonNull <T> Parser<T> with(@NonNull Function1<? super String, ? extends T> unsafeParser, @NonNull Class<T> type) {
        return new Parser<T>() {
            @Override
            public @NonNull TryResult<ParsingFailure,T> parse(@NonNull String value) {
                try {
                    final var t = unsafeParser.apply(value);
                    if (t == null) {
                        return TryResult.failure(new ParsingFailure(value,type));
                    }
                    return TryResult.success(t);
                } catch (Throwable t) {
                    ThrowableTool.interruptThreadIfCausedByInterruption(t);
                    return TryResult.failure(new ParsingFailure(value,type,t));
                }
            }

            @Override
            public @NonNull Class<T> targetType() {
                return type;
            }
        };
    }

    Parser<Integer> PARSE_INT = with(Integer::parseInt,Integer.class);
    Parser<Long> PARSE_LONG = with(Long::parseLong,Long.class);

    Parser<Float> PARSE_FLOAT = with(Float::parseFloat,Float.class);
    Parser<Double> PARSE_DOUBLE = with(Double::parseDouble,Double.class);


}
