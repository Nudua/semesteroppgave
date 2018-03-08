package com.groupname.framework.physics;

/**
 * Acceleration/Deceleration class for dealing with general movement.
 */
public class BasicAcceleration {

    private final double targetSpeed;
    private final double startSpeed;
    private final double velocity;

    private boolean isDecelerating = false;
    private boolean atTargetSpeed = false;
    private int frame = 0;

    private double speed = 0.0d;

    public BasicAcceleration(double targetSpeed, double velocity) {
        this(targetSpeed, velocity, 0.0d, false);
    }

    public BasicAcceleration(double targetSpeed, double velocity, double startSpeed,  boolean isDecelerating) {
        this.targetSpeed = targetSpeed;
        this.startSpeed = startSpeed;
        this.velocity = velocity;
        this.isDecelerating = isDecelerating;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isAtTargetSpeed() {
        return atTargetSpeed;
    }

    public int getFrame() {
        return frame;
    }

    public void reset() {
        speed = startSpeed;
        atTargetSpeed = false;
    }

    public void update() {
        frame++;

        // Don't update anything if we're already at the target speed
        if(atTargetSpeed) {
            return;
        }

        speed += velocity;

        // Check if we've accelerated/decelerated to our targetspeed
        if(isDecelerating && speed <= targetSpeed || !isDecelerating && speed >= targetSpeed) {
            speed = targetSpeed;
            atTargetSpeed = true;
        }
    }
}
