package ru.job4j.threads.nba.nonblockingcashe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public Base get(Integer id) {
        return memory.get(id);
    }

    public boolean update(Base model) {
        BiFunction<Integer, Base, Base> biFunction = new BiFunction<Integer, Base, Base>() {
            @Override
            public Base apply(Integer integer, Base base) {
                if (base.getVersion() != model.getVersion()) {
                    throw new OptimisticException("update operation: version conflict for new item:" + System.lineSeparator()
                            + model + System.lineSeparator()
                            + "current data version:" + base.getVersion() + System.lineSeparator()
                            + "new data version:" + model.getVersion() + System.lineSeparator()
                            + "item: " + model);
                }
                base.setName(model.getName());
                return base;
            }
        };
        return memory.computeIfPresent(model.getId(), biFunction) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public ArrayList<Base> getCacheList() {
        return new ArrayList(memory.values());
    }
}
