package com.groupname.game.views.controls;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class EditorItemControl extends GridPane {

    @FXML
    private Button button;

    private ObjectProperty<String> imagePath;

    public EditorItemControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(EditorItemControl.class.getResource("/com/groupname/game/views/controls/EditorItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        imagePath = new SimpleObjectProperty<>();
        //"/com/groupname/game/resources/graphics/editor/tiles/spike.png"

        setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(7), Insets.EMPTY)));


        imagePath.addListener((changed) -> {
            setBackgroundImage(imagePath.get());
            System.out.println("Changing background image");
        });
    }

    private void setBackgroundImage(String fileName) {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(EditorItemControl.class.getResourceAsStream("/com/groupname/game/resources/graphics/editor/tiles/" + fileName)), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        button.setBackground(background);
    }

    public String getImagePath() {
        return imagePath.get();
    }

    public void setImagePath(String value) {
        imagePath.set(value);
    }

    public ObjectProperty<String> imagePathProperty() {
        return imagePath;
    }


    @FXML
    protected void doSomething() {
        System.out.println("The button was clicked!");
    }
}
