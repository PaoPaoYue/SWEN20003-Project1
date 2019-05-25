package shadowbuild.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import shadowbuild.control.ClientController;
import shadowbuild.control.ServerController;
import shadowbuild.gui.componet.Button;
import shadowbuild.main.App;
import shadowbuild.player.Player;

import java.util.List;
import java.util.regex.Pattern;

public class ConnectUI extends GUI{

    private static final String IP_PATTERN = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";
    private static final String DOMAIN_PATTERN = "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$";

    private Image userPaneImg;
    private Image clientPaneImg;
    private Image hostPaneImg;

    private TextField usernameField;
    private Button clientButton;
    private Button serverButton;

    private TextField hostnameField;
    private TextField portField;
    private Button connectButton;

    private Button startButton;

    private boolean inValidUserName;
    private boolean inValidAddress;


    public ConnectUI() {
        super(UIstate.USER);

        try {
            userPaneImg = new Image("assets/gui/userPane.png");
            clientPaneImg = new Image("assets/gui/clientPane.png");
            hostPaneImg = new Image("assets/gui/hostPane.png");

            usernameField = new TextField(App.container, GUI.font, 325, 388, 380, GUI.LINE_HEIGHT);
            usernameField.setBackgroundColor(Color.white);
            usernameField.setTextColor(Color.black);

            clientButton = new Button(330,470, new Image("assets/gui/ClientButton.png"));
            clientButton.addListener((clientButton) -> {
                if (validateUserName()) {
                    ClientController.getInstance().setMainPlayer(new Player(usernameField.getText()));
                    enterState(UIstate.CLIENT);
                }
            });

            serverButton = new Button(525,470, new Image("assets/gui/HostButton.png"));
            serverButton.addListener((serverButton) -> {
                if (validateUserName()) {
                    ServerController.getInstance().setMainPlayer(new Player(0, usernameField.getText()));
                    ServerController.getInstance().startServer();
                    enterState(UIstate.SERVER);
                }
            });

            hostnameField = new TextField(App.container, GUI.font, 338, 446, 236, GUI.LINE_HEIGHT);
            portField = new TextField(App.container, GUI.font, 680, 446, 64, GUI.LINE_HEIGHT);
            hostnameField.setBackgroundColor(Color.white);
            portField.setBackgroundColor(Color.white);
            hostnameField.setTextColor(Color.black);
            portField.setTextColor(Color.black);

            connectButton = new Button(427,510, new Image("assets/gui/ConnectButton.png"));
            connectButton.addListener(connectButton -> {
                if (hostnameField.getText().isEmpty()) {
                    ClientController.getInstance().startClient("localhost", 9000);
                }
                else if (validateAddress()) {
                    ClientController.getInstance().startClient(hostnameField.getText(), Integer.parseInt(portField.getText()));
                }
            });

            startButton = new Button(427,510, new Image("assets/gui/StartButton.png"));
            startButton.addListener(startButton -> {
                ServerController.getInstance().startGame();;
            });

        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render(Graphics g) {
        if(getCurrentState().equals(UIstate.USER)) {
            g.drawImage(userPaneImg, 0, 0);
            g.setColor(Color.white);
            usernameField.render(App.container,g);
            clientButton.render(g);
            serverButton.render(g);
            if (inValidUserName) {
                g.setColor(Color.red);
                g.drawString("Invalid Username!!!", 330, 420);
            }
        } else {
            if(getCurrentState().equals(UIstate.CLIENT)) {
                switch (ClientController.getInstance().getConnectState()) {
                    case NO_CONNECTION:
                        g.drawImage(clientPaneImg, 0, 0);
                        g.setColor(Color.white);
                        hostnameField.render(App.container,g);
                        portField.render(App.container,g);
                        connectButton.render(g);
                        if (inValidAddress) {
                            g.setColor(Color.red);
                            g.drawString("Invalid Address!!!", 430, 480);
                        }
                        break;
                    case CONNECT_FAILED:
                        g.drawImage(clientPaneImg, 0, 0);
                        g.setColor(Color.white);
                        hostnameField.render(App.container,g);
                        portField.render(App.container,g);
                        connectButton.render(g);
                        if (inValidAddress) {
                            g.setColor(Color.red);
                            g.drawString("Invalid Address!!!", 430, 480);
                        } else {
                            g.setColor(Color.red);
                            g.drawString("Connect Failed!!!", 434, 480);
                        }
                        break;
                    case CONNECTING:
                        g.drawImage(hostPaneImg, 0, 0);
                        drawConnectState(ClientController.getInstance().getAllPlayers(), g);
                }
            }
            else if(getCurrentState().equals(UIstate.SERVER)) {
                g.drawImage(hostPaneImg, 0, 0);
                drawConnectState(ServerController.getInstance().getAllPlayers(), g);
                startButton.render(g);
            }
        }
    }

    private boolean validateUserName() {
        if(usernameField.getText().isEmpty()){
            inValidUserName = true;
            return false;
        }
        inValidUserName = false;
        return true;
    }

    private boolean validateAddress() {
        if (Pattern.matches(IP_PATTERN, hostnameField.getText()) || Pattern.matches(DOMAIN_PATTERN, hostnameField.getText())) {
            try {
                int port = Integer.parseInt(portField.getText());
                if (port > 0 && port < 65535)  {
                    inValidAddress = false;
                    return true;
                }
            } catch (NumberFormatException e) {}
        }
        inValidAddress = true;
        return false;
    }

    private void drawConnectState(List<Player> players, Graphics g) {
        int y = 200;
        g.setColor(Color.black);
        for(Player player: players) {
            g.drawString(Integer.toString(player.getId()), 345, y);
            g.drawString(StringUtils.center(player.getPlayerName(),20), 380, y);
            g.drawString(player.getDelay() + " ms", 640, y);
            y += GUI.LINE_HEIGHT;
        }
    }

    static class StringUtils {
        public static String center(String s, int size) {
            return center(s, size, ' ');
        }
        public static String center(String s, int size, char pad) {
            if (s == null || size <= s.length())
                return s;
            StringBuilder sb = new StringBuilder(size);
            for (int i = 0; i <(size - s.length())/2; i++) {
                sb.append(pad);
            }
            sb.append(s);
            while (sb.length() <size) {
                sb.append(pad);
            }
            return sb.toString();
        }
    }
}
