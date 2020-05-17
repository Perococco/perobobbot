package perobobbot.common.lang;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.common.lang.fp.Function1;

/**
 * @author Bastien Aracil
 * @version 19/04/2019
 */
public interface Transformer<I,O> extends Function1<I,O> {

    @NonNull
    O transform(@NonNull I input);

    @NonNull
    @Override
    default O f(@NonNull I input) {
        return transform(input);
    }

    @NonNull
    default ImmutableList<O> transform(@NonNull ImmutableList<I> inputs) {
        return ListTool.map(inputs, this);
    }

    @NonNull
    default ImmutableSet<O> transform(@NonNull ImmutableSet<I> inputs) {
        return inputs.stream().map(this).collect(SetTool.collector());
    }
}
