package playerdata;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.*;

public class player {
    private String slot;
    private String slot1 = "src/playerdata/slot1.ini";
    private String slot2 = "src/playerdata/slot2.ini";
    private String slot3 = "src/playerdata/slot3.ini";

    private String nickname;
    private String score;
    private String posX;
    private String posY;
    Map<String, String> playerTab = new HashMap<>();


    public player(String slot, String nickname, String score, String posX, String posY) {
        playerTab.put("nickname", nickname);
        playerTab.put("score", score);
        playerTab.put("posX", posX);
        playerTab.put("posY", posY);

        this.nickname = nickname;
        this.score = score;
        this.posX = posX;
        this.posY = posY;
        this.slot = slot;
    }


    public String getNick() {
        return nickname;
    }


    public void s2d() { // save to disk

            if (slot == "slot1") { //Finds the right slot to save to.
                slot = slot1;
                System.out.println("Slot 1 - Saved as " + nickname);

            } else if (slot == "slot2") {
                slot = slot2;
                System.out.println("Slot 2 - Saved as " + nickname);
            } else if (slot == "slot3") {
                slot = slot3;
                System.out.println("Slot 3 - Saved as " + nickname);
            } else {
                System.out.println("This slot do not exists: " + slot + ". Your game, " + nickname + " did not save."); //Error. Did not save.
            }

        try {
            Properties props = new Properties();
            FileOutputStream out = new FileOutputStream(slot); //Path
            for(Map.Entry<String, String> entry : playerTab.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                props.setProperty(key, value);
            }
            props.store(out, null); //Saves
            out.close(); //Close
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void restoreData() { //import data from disk

        try {
            Properties p = new Properties();
            p.load(new FileInputStream(slot));

            nickname = (String) p.get("nickname");
            score = (String) p.get("score");
            posX = (String) p.get("posX");
            posY = (String) p.get("posY");
            System.out.println(nickname);
            System.out.println(score);
            System.out.println(posX);
            System.out.println(posY);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}