package ru.job4j.threads.nba.nonblockingcashe;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}