package perobobbot.common.irc;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/**
 * @author perococco
 **/
@Value
@Builder(builderClassName = "Builder")
public class Params {

    @NonNull
    @Singular
    private final ImmutableList<String> parameters;

    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    @NonNull
    public String parameterAt(int index) {
        return parameters.get(index);
    }

    @NonNull
    public String lastParameter() {
        return parameterAt(parameters.size()-1);
    }
}
