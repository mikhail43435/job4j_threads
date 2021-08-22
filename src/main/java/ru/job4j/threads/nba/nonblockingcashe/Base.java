package ru.job4j.threads.nba.nonblockingcashe;

public class Base {
    private final int id;
    private int version;
    private String name;

    public Base(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setName(String name) {
        this.name = name;
        this.version += 1;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Base{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                '}';
    }
}