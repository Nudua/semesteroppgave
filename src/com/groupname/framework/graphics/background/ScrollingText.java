package com.groupname.framework.graphics.background;

import com.groupname.framework.core.UpdateAble;
import com.groupname.framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class slowly scroll text from the bottom of the screen to the top.
 */
public class ScrollingText implements UpdateAble {
    private Vector2D position;
    private String[] lines;

    private static final int REPEAT_COUNT = 10;
    private static final double SCROLL_SPEED = -1.0d;

    /**
     * Creates a new instance of this class with the specified credits text (lines) and the position to start the text off.
     *
     * @param lines a String array containing the text to scroll. The first line will be the header.
     * @param position the initial position of the lines.
     */
    public ScrollingText(String[] lines, Vector2D position) {
        if(lines == null) {
            throw new NullPointerException();
        }
        if(lines.length == 0) {
            throw new InvalidParameterException("Need at least 1 line to draw.");
        }

        this.lines = lines;
        this.position = Objects.requireNonNull(position);
    }

    /**
     * Draws the scrolling text at it's current position.
     *
     * @param graphicsContext the context used for drawing the text.
     */
    public void draw(GraphicsContext graphicsContext) {

        double startX = position.getX();
        double startY = position.getY();

        graphicsContext.setFill(Color.WHITE);

        double offset = 70;

        for(int x = 0; x < REPEAT_COUNT; x++) {
            for(int i = 0; i < lines.length; i++) {
                // Draw the header (first line) in a bigger font
                if(i == 0) {
                    graphicsContext.setFont(Font.font(80));
                } else {
                    graphicsContext.setFont(Font.font(50));
                }

                graphicsContext.fillText(lines[i], startX, startY);

                startY+= offset;
            }
            startY+= 720;
        }
    }

    /**
     * Scrolls the text.
     */
    @Override
    public void update() {
        position.addY(SCROLL_SPEED);
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "ScrollingText{" +
                "position=" + position +
                ", lines=" + Arrays.toString(lines) +
                '}';
    }
}
