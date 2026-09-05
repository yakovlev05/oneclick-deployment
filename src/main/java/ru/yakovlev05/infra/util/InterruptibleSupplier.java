package ru.yakovlev05.infra.util;

@FunctionalInterface
public interface InterruptibleSupplier<T> {
    T get() throws InterruptedException;
}
