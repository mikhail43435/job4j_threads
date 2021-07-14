package ru.job4j.threads.resourcesync.userstorage;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {
    private final ConcurrentHashMap<Integer, User> map = new ConcurrentHashMap<>();

    public  boolean add(User user) {
        return map.putIfAbsent(user.getId(), user) == null;
    }

    public  boolean update(User user) {
        return map.replace(user.getId(), user) != null;
    }

    public  boolean delete(User user) {
        return map.remove(user.getId(), user);
    }

    public User getUserById(int id) {
        return map.get(id);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User userFrom = map.get(fromId);
        if (!map.containsKey(fromId)
                || !map.containsKey(toId)
                || amount < 0
                || userFrom.getAmount() < amount) {
            return false;
        }
        userFrom.setAmount(userFrom.getAmount() - amount);
        User userTo = map.get(toId);
        userTo.setAmount(userTo.getAmount() + amount);
        return true;
    }
}