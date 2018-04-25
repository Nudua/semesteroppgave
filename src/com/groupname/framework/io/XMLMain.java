package com.groupname.framework.io;

import com.groupname.framework.concurrency.TaskRunner;
import com.groupname.game.editor.metadata.EnemyMetaData;
import com.groupname.game.entities.enemies.HomingEnemy;
import javafx.concurrent.Task;

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

        TaskRunner taskRunner = new TaskRunner();

        taskRunner.submit(() -> {
            System.out.println("Hi");
        }, () -> System.out.println("Done!"));

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                xmlWriter.write(Paths.get("test2.xml"), new EnemyMetaData("Test", HomingEnemy.class));
                return true;
            }
        };

        //taskRunner.submit(task);

        taskRunner.stop();
    }
}
