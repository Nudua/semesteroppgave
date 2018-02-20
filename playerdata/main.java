package playerdata;

public class main {
    public static void main(String[] args) {
        //playerdata.player ini = new playerdata.player();
        player p1 = new player("slot1","hakon", "31232", "1233","1231");
        player p2 = new player("slot2","torleiv","212", "4", "5");
        player p3 = new player("slot3", "hans", "2", "1", "23");

        p1.s2d();
        p2.s2d();
        p3.s2d();

        p1.restoreData();


        }

}
