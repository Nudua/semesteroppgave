package com.groupname.framework.graphics.background;

import com.groupname.framework.core.UpdateAble;
import com.groupname.framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class ScrollingText implements UpdateAble {
    private Vector2D position;
    private String[] lines;

    public ScrollingText() {
        position = new Vector2D(1280 / 2 - 150, 720);
        lines = new String[] {"CREDITS...", "thank", "you", "for", "playing", "our", "game", "...", "now", "we", "just", "have", "to", "make", "it", "\uD83D\uDE0A"};
    }

    public void draw(GraphicsContext graphicsContext) {

        double startX = position.getX();
        double startY = position.getY();

        graphicsContext.setFill(Color.WHITE);

        double offset = 70;

        for(int x = 0; x < 10; x++) {
            for(int i = 0; i < lines.length; i++) {

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

    public void update() {
        position.addY(-1.0d);
    }
}
