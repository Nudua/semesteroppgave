package com.groupname.framework.graphics.background;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class SierpinskiTriangleBackground {

    private GraphicsContext graphicsContext;
    private int delay = 60;
    private int counter = 0;
    private int iterations = 2;

    private final List<Color> colorList;

    public SierpinskiTriangleBackground(GraphicsContext graphicsContext) {
        this.graphicsContext = Objects.requireNonNull(graphicsContext);
        colorList = new ArrayList<>(Arrays.asList(Color.RED, Color.GREEN, Color.OLDLACE, Color.ORANGERED, Color.BLUE, Color.MAGENTA, Color.HOTPINK, Color.RED, Color.WHITE, Color.ALICEBLUE, Color.AZURE, Color.BISQUE));
    }

    // Separate logic and drawing perhaps, although probably not needed in this case
    public void update() {

    }

    private double[][] pointsToDoubleArray(Point2D top, Point2D left, Point2D right) {
        return new double[][] { {top.getX(), left.getX(), right.getX()}, { top.getY(), left.getY(), right.getY()} };
    }

    private Point2D[] buildTriangle(double x, double y, double length, boolean up) {

        Point2D top = new Point2D(x, up ? y - length : y + length);
        Point2D left = new Point2D(x - length, up ? y + length : y - length);
        Point2D right = new Point2D(x + length, up ? y + length : y - length);

        return new Point2D[] {top, left, right};
    }

    // Do something better here
    private Color getRandomColor(int index) {
        if(index >= colorList.size() - 1) {
            return colorList.get(0);
        }

        return colorList.get(index);
    }

    // Make a separate class maybe for this one?
    private void drawTriangle(Point2D top, Point2D left, Point2D right, int recursionLevels) {
        if(recursionLevels <= 2) {
            double[][] indices = pointsToDoubleArray(top, left, right);
            graphicsContext.setFill(getRandomColor(iterations));
            graphicsContext.fillPolygon(indices[0],indices[1], 3);
        } else {
            //top = 1, left = 2, right = 3
            Point2D midpointBetweenTopAndLeft = top.midpoint(left); //12
            Point2D midpointBetweenLeftAndRight = left.midpoint(right); //23
            Point2D midpointBetweenRightAndTop = right.midpoint(top); //31

            drawTriangle(top, midpointBetweenTopAndLeft, midpointBetweenRightAndTop, recursionLevels - 1);
            drawTriangle(midpointBetweenTopAndLeft, left, midpointBetweenLeftAndRight, recursionLevels - 1);
            drawTriangle(midpointBetweenRightAndTop, midpointBetweenLeftAndRight, right, recursionLevels - 1);
        }
    }

    public void draw() {

        // I should make a class for delaying
        counter++;
        if(counter == delay) {
            iterations++;

            if(iterations > 10) {
                iterations = 3;
            }
            counter = 0;
        }

        Point2D[] cords = buildTriangle(320, 350, 340, true);

        Point2D[] cords2 = buildTriangle(660, 350, 340, false);

        Point2D[] cords3 = buildTriangle(1000, 350, 340, true);

        //double[][] indices = createTriangle(cords[0], cords[1], cords[2]);

        // Draw a equilateral triangle
        graphicsContext.save();

        graphicsContext.setFill(Color.BLACK);

        drawTriangle(cords[0],cords[1],cords[2], iterations);
        drawTriangle(cords2[0],cords2[1],cords2[2], iterations);
        drawTriangle(cords3[0],cords3[1],cords3[2], iterations);
        //graphicsContext.fillPolygon(indices[0],indices[1], 3);

        graphicsContext.restore();
    }
}
