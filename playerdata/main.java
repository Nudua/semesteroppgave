package playerdata;

public class main {
    public static void main(String[] args) {

        //Creating players
        player p1 = new player("slot1","kurt", "21", "1233","-1231");
        player p2 = new player("slot2","torleiv","212", "4", "5");
        player p3 = new player("slot3", "hans", "2", "1", "23");

        //Save data to disk
        p1.s2d();
        //p2.s2d();
        //p3.s2d();

        //Restore data from disk
        p1.restoreData();
        //p2.restoreData();
        //p3.restoreData();

        }
}