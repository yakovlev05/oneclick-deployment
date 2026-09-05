package ru.yakovlev05.infra.util;

public class ThreadingUtil {

    public static <T> T callUninterruptibly(InterruptibleSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted via error. See stacktrace", e);
        }
    }

}
