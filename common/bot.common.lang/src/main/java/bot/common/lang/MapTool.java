package bot.common.lang;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * @author perococco
 **/
public class MapTool {

    @NonNull
    public static <K,V> Collector<V,?,ImmutableMap<K,V>> collector(@NonNull Function<? super V, ? extends K> keyGetter) {
        return ImmutableMap.toImmutableMap(keyGetter, v->v);
    }

    @NonNull
    public static <K,V> Collector<Map.Entry<K,V>,?,ImmutableMap<K,V>> entryCollector() {
        return ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue);
    }
}
