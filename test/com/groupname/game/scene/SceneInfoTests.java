package com.groupname.game.scene;

import com.groupname.framework.util.EmptyStringException;
import org.junit.Test;
import static org.junit.Assert.*;

public class SceneInfoTests {

    private static final String validViewPath = "/com/groupname/game/views/titleview.fxml";

    @Test(expected = NullPointerException.class)
    public void viewPathCannotBeNull() {
        new SceneInfo("", null);
    }

    @Test(expected = EmptyStringException.class)
    public void viewPathCannotBeEmpty() {
        new SceneInfo("", "");
    }

    @Test
    public void getTitleIsConsistent() {
        String title = "title";

        SceneInfo info = new SceneInfo(title, validViewPath);

        assertEquals(info.getTitle(), title);
    }

    @Test
    public void getViewPathIsConsistent() {
        SceneInfo info = new SceneInfo("title", validViewPath);

        assertEquals(info.getViewPath(), validViewPath);
    }
}
