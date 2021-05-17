package perobobbot.proxy._private;

import perobobbot.proxy._private.CustomStringifiers;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author perococco
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class ObjectStringifier {

    private final int nbMaxArrayElements;

    private static final CustomStringifiers CUSTOM_STRINGIFIERS = new CustomStringifiers();

    public static <T> void addCustomStringifier(Class<T> type, Function<? super T, String> stringifier) {
        CUSTOM_STRINGIFIERS.addStringifier(type, stringifier);
    }

    public ObjectStringifier(int nbMaxArrayElements) {
        this.nbMaxArrayElements = nbMaxArrayElements;
    }

    public ObjectStringifier() {
        this(-1);
    }


    public String[] toStrings(Object[] objects) {
        if (objects == null) {
            return null;
        }
        final Map<Object, Integer> references = new IdentityHashMap<>();
        final String[] result = new String[objects.length];
        for (int i = 0; i < objects.length; i++) {
            result[i] = toString(new StringBuilder(), objects[i], references).toString();
        }
        return result;
    }

    public String toString(Object object) {
        return toString(new StringBuilder(), object, new IdentityHashMap<>()).toString();
    }

    private StringBuilder toString(StringBuilder sb, Object object, Map<Object, Integer> references) {
        if (object == null) {
            return sb.append("null");
        }

        Integer pos = references.get(object);
        if (pos != null) {
            return sb.append('#').append(pos);
        }

        final Class<?> objectClass = object.getClass();

        if (objectClass.isArray()) {
            references.put(object, references.size() + 1);
            if (objectClass.getComponentType().isPrimitive()) {
                primitiveArrayToString(sb, object, references);
            } else {
                toString(sb, (Object[]) object, references);
            }
        } else if (Map.class.isAssignableFrom(objectClass)) {
            references.put(object, references.size() + 1);
            toString(sb, (Map<?, ?>) object, references);
        } else if (Path.class.isAssignableFrom(objectClass)) {
            //handle path separately as they are infinite iterable
            references.put(object, references.size() + 1);
            sb.append(String.valueOf(object));
        } else if (Iterable.class.isAssignableFrom(objectClass)) {
            references.put(object, references.size() + 1);
            toString(sb, (Iterable<?>) object, references);
        } else if (CharSequence.class.isAssignableFrom(objectClass)) {
            sb.append('"').append(object.toString()).append('"');
        } else if (Byte.class == objectClass || byte.class == objectClass) {
            sb.append(String.format("%02X", (Byte) object));
        } else {
            sb.append(CUSTOM_STRINGIFIERS.toString(object, String::valueOf));
        }

        return sb;
    }

    private void toString(StringBuilder sb, Iterable<?> object, Map<Object, Integer> map) {
        String sep = "[";
        for (Object o : object) {
            sb.append(sep);
            toString(sb, o, map);
            sep = ", ";
        }
        sb.append("]");
    }

    private void toString(StringBuilder sb, Map<?, ?> object, Map<Object, Integer> map) {
        String sep = "{";
        for (Map.Entry<?, ?> entry : object.entrySet()) {
            sb.append(sep);
            toString(sb, entry.getKey(), map);
            sb.append("=");
            toString(sb, entry.getValue(), map);
            sep = ", ";
        }
        sb.append("}");
    }

    private void primitiveArrayToString(StringBuilder sb, Object primitiveArray, Map<Object, Integer> map) {
        final int size = Array.getLength(primitiveArray);
        if (size == 0) {
            sb.append("[]");
            return;
        }
        sb.append("{").append(size).append("}");
        final boolean limited = this.nbMaxArrayElements >= 0 && size > this.nbMaxArrayElements;
        final int limitedSize = limited ? this.nbMaxArrayElements : size;

        String sep = "[";
        for (int i = 0; i < limitedSize; i++) {
            sb.append(sep);
            toString(sb, Array.get(primitiveArray, i), map);
            sep = " ";
        }

        if (limited) {
            sb.append("... ");
        }
        sb.append(']');
    }


    private void toString(StringBuilder sb, Object[] object, Map<Object, Integer> map) {
        final int size = object.length;
        if (size == 0) {
            sb.append("[]");
            return;
        }

        sb.append("{").append(size).append("}");
        if (this.nbMaxArrayElements == -1 || size <= this.nbMaxArrayElements) {
            String sep = "[";
            for (Object anA : object) {
                sb.append(sep);
                toString(sb, anA, map);
                sep = ", ";
            }
        } else {
            String sep = "[";
            for (int i = 0; i < nbMaxArrayElements; i++) {
                sb.append(sep);
                toString(sb, object[i], map);
                sep = ", ";
            }
            sb.append("... ");
        }
        sb.append(']');
    }

}
