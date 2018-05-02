package com.groupname.game.editor.controls;

import com.groupname.game.editor.metadata.ObjectMetaData;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * This class represents a custom ListCell for the ObjectMetaData class.
 */
public class MetaDataListCell extends ListCell<ObjectMetaData> {
    @Override
    protected void updateItem(ObjectMetaData item, boolean empty) {
        super.updateItem(item, empty);

        if(item != null) {
            Label label = new Label(item.getName());
            label.setWrapText(true);
            label.setPrefSize(100,100);
            label.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderStroke.MEDIUM)));
            setGraphic(label);
        }
    }
}
