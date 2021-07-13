package ru.job4j.threads.resourcesync;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    private final List<T> list = new ArrayList<>();

    public void add(T value) {
        list.add(value);
    }

    public T get(int index) {
        return list.get(index);
    }

    private List<T> copy(List<T> list) {
        List<T> result = new ArrayList<>();
        result.addAll(list);
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return copy(this.list).iterator();
    }
}