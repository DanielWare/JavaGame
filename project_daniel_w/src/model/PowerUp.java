package model;

import view.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class PowerUp extends GameFigure {

    public static int powerAlive;
    public static int powerDead;
    private int radius;
    private int dx = 3;
    private int dy = 3;
    
    private Image powerImage;
    
    PowerState pState;
    
    public PowerUp(float x, float y, int radius, Color color) {
        super(x, y);
        super.state = STATE_ALIVE;
        super.alliance = ALLIANCE_POWER;
        this.radius = radius;
        powerAlive++;
        pState = new PowerStateAlive();
        
        try {
            powerImage = ImageIO.read(getClass().getResource("block.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open block.png");
            System.exit(-1);
        }
        
    }

    @Override
    public void render(Graphics2D g) {
        if(pState instanceof PowerStateAlive){
            g.drawImage(powerImage, (int)super.x, (int)super.y, radius * 2, radius * 2, null);
        } else {
            g.setColor(Color.WHITE);
            g.fillOval((int)super.x, (int)super.y, radius, radius);
        }
    }

    @Override
    public void update() {
        // ball bounces on the wall
        super.x += dx;
        super.y += dy;

        if (super.x + radius > GamePanel.width) {
            if(pState instanceof PowerStateUsed){
                nextState();
            }
            dx = -dx;
            super.x = GamePanel.width - radius;
        } else if (super.x - radius < 0) {
            if(pState instanceof PowerStateUsed){
                nextState();
            }
            dx = -dx;
            super.x = radius;
        }

        if (super.y + radius > GamePanel.height) {
            if(pState instanceof PowerStateUsed){
                nextState();
            }
            dy = -dy;
            super.y = GamePanel.height - radius;
        } else if (super.y - radius < 0) {
            if(pState instanceof PowerStateUsed){
                nextState();
            }
            dy = -dy;
            super.y = radius;
        }
    }
    
    @Override
    public Rectangle2D getCollisionBox() {
        double colX = super.x - ((radius * 2) *.9 / 2);
        double colY = super.y - ((radius * 2)*.9 / 2);
        double colW = (radius * 2) * .9;
        double colH = (radius * 2) * .9;
        if(pState instanceof PowerStateAlive){
            return new Rectangle2D.Double(colX, colY, colW, colH);
        } else {
            return new Rectangle2D.Double(-50, -50, 0, 0);
        }
        
    }

    @Override
    public void addDead() {
        powerDead++;
        powerAlive--;
    }

    @Override
    public void powerUpCollected() {
        //not used
    }

    public void setState(PowerState state){
        pState = state;
    }
    
    @Override
    public void nextState() {
        pState.goNextState(this);
    }
    
}
