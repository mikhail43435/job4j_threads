package ru.job4j.threads.atom.memory;

public class Node<T> {
    private Node<T> next;
    private T value;

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        if (this.next == null) this.next = next;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (this.value == null) this.value = value;
    }
}