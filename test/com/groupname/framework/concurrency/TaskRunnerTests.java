package com.groupname.framework.concurrency;

import javafx.concurrent.Task;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class TaskRunnerTests {

    @Test(expected = NullPointerException.class)
    public void executorServiceCannotBeNull() {
        new TaskRunner(null);
    }

    @Test(expected = NullPointerException.class)
    public void submitSupplierCannotBeNull() {
        TaskRunner taskRunner = new TaskRunner();

        Supplier<Boolean> supplier = null;
        Consumer<Boolean> consumer = (b) ->  { System.out.println(b); };

        taskRunner.submit(supplier, consumer);
    }

    @Test(expected = NullPointerException.class)
    public void submitConsumerCannotBeNull() {
        TaskRunner taskRunner = new TaskRunner();

        Supplier<Boolean> supplier = () -> { return true; };
        Consumer<Boolean> consumer = null;

        taskRunner.submit(supplier, consumer);
    }

    @Test(expected = NullPointerException.class)
    public void submitRunnableCannotBeNull() {
        TaskRunner taskRunner = new TaskRunner();

        taskRunner.submit(null);
    }

    @Test(expected = NullPointerException.class)
    public void submitRunnableActionCannotBeNull() {
        TaskRunner taskRunner = new TaskRunner();

        Runnable action = null;
        Runnable onCompleted = () -> {};

        taskRunner.submit(action, onCompleted);
    }

    @Test(expected = NullPointerException.class)
    public void submitRunnableOnCompletedCannotBeNull() {
        TaskRunner taskRunner = new TaskRunner();

        Runnable action = () -> {};
        Runnable onCompleted = null;

        taskRunner.submit(action, onCompleted);
    }

    @Test(expected = NullPointerException.class)
    public void submitTaskCannotBeNull() {
        TaskRunner taskRunner = new TaskRunner();

        Task<Boolean> task = null;

        taskRunner.submit(task);
    }

}
