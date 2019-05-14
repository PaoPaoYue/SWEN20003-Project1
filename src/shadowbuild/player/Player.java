package shadowbuild.player;

public class Player {
    private String playerName;
    private PlayerInfo info;

    public Player(String playerName) {
        this.playerName = playerName;
        info = new PlayerInfo();
    }

    public String getPlayerName() {
        return playerName;
    }

    public PlayerInfo getInfo() {
        return info;
    }
}
