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
 * Convenience class for running methods on a thread using various ExecutorService types.
 * The default ExecutorService for this class is the CachedThreadPool one.
 *
 * This class uses the fail-fast principle and will throw a NullPointerException if any of the calling parameters are null.
 *
 * Users must call stop() before exiting the program to avoid the ExecutorService from stalling.
 */
public class TaskRunner {
    private final ExecutorService executor;

    /**
     * Creates a new instance of the TaskRunner with the default ExecutorService (CachedThreadPool)
     */
    public TaskRunner() {
        executor = Executors.newCachedThreadPool();
    }

    /**
     * Creates a new instance of this class with the given ExecutorService.
     *
     * @param executor the ExecutorService to use internally.
     */
    public TaskRunner(ExecutorService executor) {
        this.executor = Objects.requireNonNull(executor);
    }

    /**
     * Returns if the ExecutorService is shutDown or not.
     *
     * @return true if the ExecutorService is Shutdown, false if it is still running.
     */
    public boolean isShutdown() {
        return executor.isShutdown();
    }

    /**
     * Submit a new Supplier of type T that should be run on a separate thread
     * using the ExcecutorService.
     *
     * The onCompleted Consumer of type T will be invoked on the JavaFX UI Thread
     * after the Supplier of type T has completed.
     *
     * @param action the method to run on a separate thread.
     * @param onCompleted the method to invoke when the action Supplier has completed.
     * @param <T> the type of class used for the Supplier and Consumer.
     */
    public <T> void submit(Supplier<T> action, Consumer<T> onCompleted) {
        Objects.requireNonNull(action);
        Objects.requireNonNull(onCompleted);

        executor.submit(() -> {
           T returnValue = action.get();

           Platform.runLater(() -> onCompleted.accept(returnValue));
        });
    }

    /**
     * A fire and forget method that runs the specified Runnable on a separate thread.
     *
     * @param action the runnable to run on a separate thread.
     */
    public void submit(Runnable action) {
        Objects.requireNonNull(action);
        executor.submit(action);
    }

    /**
     * Submit a Runnable to be run on a separate thread, with a supplied onCompleted
     * Runnable that will be called on the JavaFX UI thread when the action Runnable has completed.
     *
     * @param action the Runnable to run on a separate thread.
     * @param onCompleted the Runnable to invoke on the JavaFX thread when the action runnable has completed.
     */
    public void submit(Runnable action, Runnable onCompleted) {
        Objects.requireNonNull(action);
        Objects.requireNonNull(onCompleted);

        executor.submit(() -> {
            action.run();

            // Invoke back on the javafx thread
            Platform.runLater(onCompleted);
        });
    }

    /**
     * Submits a new Task that will be run on the ExecutorService.
     *
     * @param task the task to run on the ExecutorService.
     * @param <V> the generic class parameter that the Task contains.
     */
    public <V> void submit(Task<V> task) {
        Objects.requireNonNull(task);

        executor.submit(task);
    }

    /**
     * Attempts to shutdown the ExecutorService, it will wait for 2000 milliseconds
     * before it will force all the currently running actions to quit forcefully.
     *
     * @throws InterruptedException if the termination was interrupted.
     */
    public void stop() throws InterruptedException {
        if(executor.isShutdown()) {
            return;
        }

        executor.shutdown();
        executor.awaitTermination(2000, TimeUnit.MILLISECONDS);
        executor.shutdownNow();
    }

    /**
     * Returns the String representation of this TaskRunner
     * this method only returns the type of the executor used in this class.
     *
     * @return the type of the executor used in this class.
     */
    @Override
    public String toString() {
        return "TaskRunner{" +
                "executor=" + executor +
                '}';
    }
}
