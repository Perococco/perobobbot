package perobobbot.lang;

import lombok.NonNull;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author perococco
 **/
public class IdentityHashSet<E> implements Set<E> {

    public static <S> IdentityHashSet<S> empty() {
        return new IdentityHashSet<>();
    }

    @NonNull
    private final IdentityHashMap<E,E> backupMap = new IdentityHashMap<>();

    @Override
    public int size() {
        return backupMap.size();
    }

    @Override
    public boolean isEmpty() {
        return backupMap.isEmpty();
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public boolean contains(Object o) {
        return backupMap.containsKey(o);
    }

    @Override
    public @NonNull Iterator<E> iterator() {
        return backupMap.keySet().iterator();
    }

    @Override
    public Object @NonNull [] toArray() {
        return backupMap.keySet().toArray();
    }

    @Override
    public <T> T @NonNull [] toArray(T @NonNull [] a) {
        //noinspection SuspiciousToArrayCall
        return backupMap.keySet().toArray(a);
    }

    @Override
    public boolean add(E e) {
        return null == backupMap.put(e,e);
    }

    @Override
    public boolean remove(Object o) {
        return backupMap.keySet().remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return backupMap.keySet().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return backupMap.keySet().retainAll(c);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return backupMap.keySet().removeAll(c);
    }

    @Override
    public void clear() {
        backupMap.clear();
    }
}
