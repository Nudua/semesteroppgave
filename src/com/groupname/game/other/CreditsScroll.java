package com.groupname.game.other;

import com.groupname.framework.math.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// Don't do this ever
public class CreditsScroll {
    private Vector2D position;
    private String[] lines;

    public CreditsScroll() {
        position = new Vector2D(1280 / 2 - 150, 720);
        lines = new String[] {"Credits...", "thank", "you", "for", "playing", "our", "game", "...", "now", "we", "just", "have", "to", "make", "it", "\uD83D\uDE0A"};
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
