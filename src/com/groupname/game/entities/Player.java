package com.groupname.game.entities;
import com.groupname.framework.graphics.Sprite;
import com.groupname.framework.graphics.drawing.SpriteBatch;
import com.groupname.framework.input.InputManager;
import com.groupname.framework.input.devices.KeyboardInput;
import com.groupname.framework.math.Vector2D;
import com.groupname.game.entities.Actor;

public class Player extends Actor {
    private final InputManager inputManager;
    private final Vector2D initialPosition;
    private double speed = 2.5d;

    public Player(Sprite sprite, Vector2D position, int hitPoints, InputManager inputManager) {
        super(sprite, position, hitPoints);
        this.inputManager = inputManager;
        initialPosition = new Vector2D(position);
    }

    @Override
    public void onCollide() {

    }
    @Override
    public void reset() {
        position.setX(initialPosition.getX());
        position.setY(initialPosition.getY());
    }
    @Override
    public void onDeath() {

    }
    @Override
    public void update() {
        double x = position.getX();
        double y = position.getY();
        if(inputManager.isDown(KeyboardInput.Defaults.LEFT)) {
            position.setX(x - speed);
        } else if (inputManager.isDown((KeyboardInput.Defaults.RIGHT))) {
            position.setX(x + speed);
        }
        if(inputManager.isDown(KeyboardInput.Defaults.UP)) {
            position.setY(y - speed);
        } else if (inputManager.isDown(KeyboardInput.Defaults.DOWN)) {
            position.setY(y + speed);
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {

    }
}