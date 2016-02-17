package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import view.GamePanel;


public class Background {
    
    private Image bgImage1;
    private Image bgImage2;

    private float width;
    private float height;
    private float y;
    
    private float scrollAmount = 3.0f;
    
    Background(){
        height = GamePanel.height;
        width = GamePanel.width;
        y = 0;
        bgImage1 = null;
        bgImage2 = null;
        try {
            bgImage1 = ImageIO.read(getClass().getResource("Parallax60.png"));
            bgImage2 = ImageIO.read(getClass().getResource("Parallax60.png"));
        } catch (IOException ex){
            JOptionPane.showMessageDialog(null, "Error: Cannot open bgImage.png");
            System.exit(-1);
        }
    }
    
    public void update(){
        height = GamePanel.height;
        width = GamePanel.width;
        y += scrollAmount;
        if(y > GamePanel.height){
            y = y - height;
        }
    }
    
    public void render(Graphics2D g){
        g.drawImage(bgImage1, 0, (int)(y)         , (int)width, (int)height, null);
        g.drawImage(bgImage2, 0, (int)(y - height), (int)width, (int)height, null);

    }
    
}
