package com.groupname.framework.concurrency;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;


/**
 * Convenience class for running short-lived runnables and tasks on a separate thread using the ExecutorService interface.
 *
 * Supports methods for running any methods that implement runnable or
 */
public class TaskRunner {
    private final ExecutorService executor;

    public TaskRunner() {
        executor = Executors.newCachedThreadPool();
    }

    public TaskRunner(ExecutorService executor) {
        this.executor = Objects.requireNonNull(executor);
    }

    public boolean isShutdown() {
        return executor.isShutdown();
    }

    public <T> void submit(Supplier<T> action, Consumer<T> onCompleted) {
        Objects.requireNonNull(action);
        Objects.requireNonNull(onCompleted);

        executor.submit(() -> {
           T returnValue = action.get();

           Platform.runLater(() -> onCompleted.accept(returnValue));
        });
    }

    public void submit(Runnable action) {
        Objects.requireNonNull(action);
        executor.submit(action);
    }

    public void submit(Runnable action, Runnable onCompleted) {
        Objects.requireNonNull(action);
        Objects.requireNonNull(onCompleted);

        executor.submit(() -> {
            action.run();

            // Invoke back on the javafx thread
            Platform.runLater(onCompleted);
        });
    }

    public <V> void submit(Task<V> task) {
        Objects.requireNonNull(task);

        executor.submit(task);
    }

    public void stop() throws InterruptedException {
        if(executor.isShutdown()) {
            return;
        }

        executor.shutdown();
        executor.awaitTermination(2000, TimeUnit.MILLISECONDS);
        executor.shutdownNow();
    }

}
