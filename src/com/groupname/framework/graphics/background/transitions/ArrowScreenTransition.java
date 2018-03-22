package com.groupname.framework.graphics.background.transitions;

import com.groupname.framework.graphics.background.transitions.ScreenTransition;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;



// Screen transition?
public class ArrowScreenTransition implements ScreenTransition {
    private GraphicsContext graphicsContext;
    private double radius = 100;

    private Point2D triangleStartPos = new Point2D(-200, 720 / 2);
    private Point2D trianglePosition = triangleStartPos;
    private boolean done = false;

    public boolean isDone() {
        return done;
    }

    // Dont do this generally
    /*
    public void setDone(boolean done) {
        this.done = done;
        trianglePosition = triangleStartPos;
    }
    */

    public void reset() {
        done = false;
        trianglePosition = triangleStartPos;
    }

    public ArrowScreenTransition(GraphicsContext graphicsContext) {
        this.graphicsContext = Objects.requireNonNull(graphicsContext);
    }

    public void update() {
        radius+= 5;

        if(radius > 2000) {
            radius = 1;
        }

        trianglePosition = trianglePosition.add(60,0);


        if(trianglePosition.getX() > 1280 + 400) {
            //trianglePosition = triangleStartPos;
            done = true;
        }

    }


    public void draw() {
        graphicsContext.save();

        //graphicsContext.setFill(Color.BLACK);
        //graphicsContext.fillRect(0 ,0, 1280, 720);

        drawTriangle();

        graphicsContext.restore();
    }

    private void drawTriangle() {

        graphicsContext.setFill(Color.BLACK);
        Point2D[] triangle = buildTriangle(trianglePosition.getX(), trianglePosition.getY(), 720 / 2);

        //triangle[0] = triangle[0].add(trianglePosition);
        //triangle[1] = triangle[1].add(trianglePosition);
        if(trianglePosition.getX() > 0) {
            if(!done) {
                graphicsContext.fillRect(0,0, trianglePosition.getX(), 720);
            }
        }

        double[][] triangleCords = pointsToDoubleArray(triangle[0], triangle[1], triangle[2]);

        graphicsContext.fillPolygon(triangleCords[0], triangleCords[1], 3);
    }

    private double[][] pointsToDoubleArray(Point2D top, Point2D left, Point2D right) {
        return new double[][] { {top.getX(), left.getX(), right.getX()}, { top.getY(), left.getY(), right.getY()} };
    }

    private Point2D[] buildTriangle(double x, double y, double length) {

        Point2D top = new Point2D(x, y - length);
        Point2D bottom = new Point2D(x, y + length);
        Point2D middleRight = new Point2D(x + length, y);

        return new Point2D[] {top, bottom, middleRight};
    }

    private void drawCircle() {


        //graphicsContext.setFill(Color.RED);
        //graphicsContext.fillRect(0, 0, 1280, 720);

        Point2D center = new Point2D(1280 / 2, 720 / 2);
        graphicsContext.setFill(Color.HOTPINK);
        graphicsContext.fillOval(center.getX() - radius / 2, center.getY() - radius / 2, radius, radius);

        int r = 100;
        graphicsContext.setFill(Color.RED);
        graphicsContext.setStroke(Color.MAGENTA);
        graphicsContext.strokeOval(center.getX() - r / 2, center.getY() - r / 2, r, r);


    }

}
