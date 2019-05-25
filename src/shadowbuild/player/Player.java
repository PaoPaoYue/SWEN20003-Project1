package shadowbuild.player;

public class Player {
    private int id;
    private String playerName;
    private int delay;
    private boolean connected;
    private PlayerGameInfo info;

    public Player(String playerName) {
        this.playerName = playerName;
        info = new PlayerGameInfo();
    }

    public Player(int id, String playerName) {
        this.id = id;
        this.playerName = playerName;
        info = new PlayerGameInfo();
    }

    public String getPlayerName() {
        return playerName;
    }

    public PlayerGameInfo getGameInfo() {
        return info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj instanceof Player) return ((Player) obj).getId() == id;
        return false;
    }
}
