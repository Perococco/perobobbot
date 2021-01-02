package perobobbot.command;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.Parser;

import java.util.Optional;

public interface CommandParsing {

    /**
     * @return the full synthetic name of the command
     */
    @NonNull String getFullName();

    /**
     * @return the values associated with the parsed parameters
     */
    @NonNull ImmutableMap<String,String> getParameterValues();

    /**
     * @return the number of parameters found during the parsing
     */
    default int getNumberOfParameters() {
        return getParameterValues().size();
    }

    /**
     * @return the set of parameter names
     */
    default @NonNull ImmutableSet<String> getParameterNames() {
        return getParameterValues().keySet();
    }

    /**
     * @param parameterName the name of a parameter
     * @return an optional containing the value of the parameter with the provided name if it exits, an emptyu optional otherwise
     */
    default @NonNull Optional<String> findParameter(@NonNull String parameterName) {
        return Optional.ofNullable(getParameterValues().get(parameterName));
    }

    /**
     * @param parameterName the name of a parameter
     * @return the value of the parameter with the provided name if it exits
     * @throw UnknownParameter if the parameter does not exist
     */
    default @NonNull String getParameter(@NonNull String parameterName) {
        return findParameter(parameterName).orElseThrow(()-> new UnknownParameter(getFullName(), parameterName));
    }

    default @NonNull <T> Optional<T> findParameter(@NonNull String parameterName, @NonNull Parser<T> parser) {
        return findParameter(parameterName).map(parser::parse).flatMap(t -> t.success());
    }

    default @NonNull <T> T getParameter(@NonNull String parameterName, @NonNull Parser<T> parser) {
        return parser.parse(getParameter(parameterName)).get();
    }


    default @NonNull Optional<Integer> findIntParameter(@NonNull String parameterName) {
        return findParameter(parameterName,Parser.PARSE_INT);
    }

    default @NonNull int getIntParameter(@NonNull String parameterName) {
        return getParameter(parameterName,Parser.PARSE_INT);
    }



}
