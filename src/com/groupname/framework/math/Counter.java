package com.groupname.framework.math;

import java.security.InvalidParameterException;

/**
 * A frame based counter.
 */
public class Counter {
    private int delay;
    private int counter;

    /**
     * Frame based counter that takes seconds and turn it in to frames.
     *
     * @param seconds how long you want it to count.
     */
    public Counter(int seconds) {
        if(seconds <= 0) {
            throw new InvalidParameterException();
        }

        delay = seconds * 60;

    }

    /**
     * Method that steps up the counter.
     */
    public void step(){
        counter++;
    }

    /**
     * Getter for getting the value of the counter.
     *
     * @return the frame its on.
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Checks if the counter is done.
     *
     * @return return true when it is done.
     */
    public boolean isDone() {
        return counter >= delay;

    }

    /**
     * Method for resetting the counter so its ready for reuse.
     */
    public void reset() {
        counter = 0;
    }

    /**
     * Returns the String representation of this instance.
     *
     * @return String representation of this instance.
     */
    @Override
    public String toString() {
        return "Counter{" +
                "delay=" + delay +
                ", counter=" + counter +
                '}';
    }
}
