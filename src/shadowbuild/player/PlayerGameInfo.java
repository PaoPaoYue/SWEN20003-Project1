package shadowbuild.player;

public class PlayerGameInfo {

    private int metalAmount;
    private int unobtainiumAmount;

    public int getMetalAmount() {
        return metalAmount;
    }

    public int getUnobtainiumAmount() {
        return unobtainiumAmount;
    }

    public boolean changeMetalAmount(int delta) {
        if(metalAmount + delta < 0) {
            return false;
        } else {
            metalAmount += delta;
            return true;
        }
    }

    public boolean changeUnobtainiumAmount(int delta) {
        if(unobtainiumAmount + delta < 0) {
            return false;
        } else {
            unobtainiumAmount += delta;
            return true;
        }
    }
}
