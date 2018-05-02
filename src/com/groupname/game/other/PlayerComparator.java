package com.groupname.game.other;

import com.groupname.game.editor.metadata.ObjectMetaData;
import com.groupname.game.entities.Player;

import java.util.Comparator;

/**
 * A custom comparator to order GameObjects of the Player instance first.
 */
public class PlayerComparator implements Comparator<ObjectMetaData> {
    @Override
    public int compare(ObjectMetaData item1, ObjectMetaData item2) {
        if(item1.getType() == Player.class) {
            return -1;
        }

        if(item2.getType() == Player.class) {
            return 1;
        }

        return 0;
    }
}
