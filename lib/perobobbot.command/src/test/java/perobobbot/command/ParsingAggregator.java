package perobobbot.command;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

public class ParsingAggregator implements ArgumentsAggregator {

    @Override
    public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
        return Parsing.builder()
                      .fullCommand(argumentsAccessor.getString(1))
                      .nbParameters(argumentsAccessor.getInteger(2))
                      .regexp(argumentsAccessor.getString(3))
                      .build();
    }
}
