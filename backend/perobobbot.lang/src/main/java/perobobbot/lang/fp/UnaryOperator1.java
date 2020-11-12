package perobobbot.lang.fp;

import lombok.NonNull;

public interface UnaryOperator1<A> extends Function1<A,A> {

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <T> the type of the input and output of the operator
     * @return a unary operator that always returns its input argument
     */
    @NonNull
    static <T> UnaryOperator1<T> identity() {
        return t -> t;
    }


}
