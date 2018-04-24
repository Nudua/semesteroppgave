package com.groupname.framework.io;

import com.groupname.framework.math.Vector2D;
import com.groupname.game.editor.metadata.EnemyMetaData;
import com.groupname.game.editor.metadata.ObjectMetaData;
import com.groupname.game.editor.metadata.PowerUpMetaData;
import com.groupname.game.entities.SpriteFactory;
import com.groupname.game.entities.enemies.GuardEnemy;
import com.groupname.game.entities.enemies.HomingEnemy;
import com.groupname.game.entities.enemies.TowerEnemy;
import com.groupname.game.entities.powerups.HeartPowerUp;
import javafx.scene.shape.Path;

import java.nio.file.Paths;

public class XMLMain {

    public static void main (String[] args) throws Exception {
        Content.setContentBaseFolder("/com/groupname/game/resources");

        XMLWriter xmlWriter = new XMLWriter();

        XMLNode node = new XMLNode("Tor", "Håkon");

        //xmlWriter.write(Paths.get("test.xml"), node);
        //xmlWriter.write(Paths.get("test.xml"), new Vector2D());
        //xmlWriter.write(Paths.get("test.xml"), new SpriteFactory());
        //xmlWriter.write(Paths.get("test.xml"), new ObjectMetaData("OSLOMET", GuardEnemy.class));
        //xmlWriter.write(Paths.get("test.xml"), new PowerUpMetaData("Test", HeartPowerUp.class, 3));
        xmlWriter.write(Paths.get("test2.xml"), new EnemyMetaData("Test", HomingEnemy.class));



    }
}
