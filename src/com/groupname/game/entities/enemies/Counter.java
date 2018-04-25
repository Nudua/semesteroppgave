package com.groupname.game.entities.enemies;

public class Counter {
    private int delay;
    private int counter;


    public Counter(int seconds) {
        delay = seconds * 60;

    }

    public void step(){
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    public boolean isDone() {
        return counter >= delay;

    }

    public void reset() {
        counter = 0;
    }
}
