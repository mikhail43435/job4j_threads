package ru.job4j.threads.resourcesync.userstorage;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class UserStorage {
    private final ConcurrentHashMap<Integer, User> map = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    public boolean add(User user) {
        if (map.containsValue(user)) {
            return false;
        }
        map.put(idCounter.incrementAndGet(), user);
        return true;
    }

    public boolean update(User user) {
        int userId = getUserId(user);
        if (userId > 0) {
            map.put(userId, user);
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(User user) {
        int userId = getUserId(user);
        if (userId > 0) {
            map.remove(userId);
            return true;
        } else {
            return false;
        }
    }

    public User getUserById(int id) {
        return map.get(id);
    }

    private int getUserId(User user) {
        if (!map.containsValue(user)) {
            return -1;
        }
        for (Map.Entry<Integer, User> entry : map.entrySet()) {
            if (Objects.equals(user, entry.getValue())) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public boolean isInStorage(User user) {
        return map.containsValue(user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (!map.containsKey(fromId) || !map.containsKey(toId) || amount < 0) {
            return false;
        }
        User userFrom = map.get(fromId);
        userFrom.setAmount(userFrom.getAmount() - amount);
        User userTo = map.get(toId);
        userTo.setAmount(userTo.getAmount() + amount);
        return true;
    }
}