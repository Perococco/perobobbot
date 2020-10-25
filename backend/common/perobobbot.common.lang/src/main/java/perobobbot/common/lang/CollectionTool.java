package perobobbot.common.lang;

import com.google.common.collect.ImmutableList;
import perococco.perobobbot.common.lang.SplitterAccumulator;

import java.util.stream.Collector;

public class CollectionTool {

    public static <T> Collector<T,?, ImmutableList<ImmutableList<T>>> split(int groupSize) {
        return Collector.of(
                () -> new SplitterAccumulator<>(groupSize),
                SplitterAccumulator<T>::addElement,
                SplitterAccumulator<T>::combine,
                SplitterAccumulator<T>::build
        );
    }
}
