package shadowbuild.gui.componet;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.TextField;
import shadowbuild.gui.GUI;
import shadowbuild.main.App;
import shadowbuild.util.Rect;

import java.util.LinkedList;
import java.util.Queue;

public class Textbox {

    private final static int MARGIN_HORIZONTAL = 5;
    private final static int MARGIN_VERTICAL = 8;
    private final static int GAP = 5;

    private static Color BackGroundColor = new Color(200,200,200,100);

    private int lines;
    private int len;
    private TextField inputField;
    private Rect displayField;
    private Queue<String> textQueue;

    public Textbox(int x, int y, int lines, int len) {
        this.lines = lines;
        this.len = len;
        textQueue = new LinkedList<>();

        inputField = new TextField(App.container, App.container.getDefaultFont(),x, y, MARGIN_HORIZONTAL*2+len* GUI.CHAR_WIDTH, GUI.LINE_HEIGHT);
        inputField.addListener((inputField) -> {
            addMessage(((TextField) inputField).getText());
            ((TextField) inputField).setText("");
        });

        inputField.setBackgroundColor(BackGroundColor);
        inputField.setBorderColor(Color.white);
        inputField.setTextColor(Color.white);

        displayField = new Rect(x, y - (MARGIN_VERTICAL*2+lines*GUI.LINE_HEIGHT) -
                GAP, MARGIN_HORIZONTAL*2+len*GUI.CHAR_WIDTH, MARGIN_VERTICAL*2+lines*GUI.LINE_HEIGHT);

    }

    public void render(Graphics g){
        Color tmpColor = g.getColor();
        g.setColor(Color.white);
        inputField.render(App.container, g);
        g.setColor(BackGroundColor);
        g.fillRect((float)displayField.getX(), (float)displayField.getY(), (float)displayField.getWidth(), (float)displayField.getHeight());
        g.setColor(Color.white);
        float textX = (float)displayField.getX() + MARGIN_HORIZONTAL;
        float textY = (float)displayField.getY() + MARGIN_VERTICAL;
        for (String text: textQueue) {
            g.drawString(text,textX, textY);
            textY += GUI.LINE_HEIGHT;
        }
        g.setColor(tmpColor);
    }

    public void addMessage(String message) {
        do{
            if (message.length() < len) {
                textQueue.offer(message);
                if(textQueue.size() > lines) textQueue.poll();
                break;
            }
            textQueue.offer(message.substring(0,len));
            if(textQueue.size() > lines) textQueue.poll();
            message = message.substring(len);
        }while(message.length() > 0);
    }

    public Boolean isAcceptingInput() {
        return inputField.isAcceptingInput();
    }


}
