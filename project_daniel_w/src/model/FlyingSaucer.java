
package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import view.GamePanel;

public class FlyingSaucer extends GameFigure {
    
    public static int saucersAlive;
    public static int saucersDead;
    private int width = 40;
    private int height = 10;
    private final int UNIT_TRAVEL = 5; // per frame
    private int powerUpsCollected = 0;
    private final int POWER_UP_THRESHOLD = 1;
    
    private Image saucerImage;
    
    private int direction = 1; // +1: to the right; -1 to the left
    
    SaucerState sState;
    
    
    public FlyingSaucer(float x, float y) {
        super(x, y);
        super.state = STATE_ALIVE;
        super.alliance = ALLIANCE_ENEMY;
        saucersAlive++;
        sState = new SaucerStateAlive();
        
        try {
            saucerImage = ImageIO.read(getClass().getResource("ufo.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open ufo.png");
            System.exit(-1);
        }
        
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(saucerImage, (int)super.x, (int)super.y, width, height, null);

    }

    @Override
    public void update() {
        updateState();
        if(powerUpsCollected > 0 && powerUpsCollected % POWER_UP_THRESHOLD == 0){
            powerUpsCollected = 0;
            width+=2;
            height+=2;
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        double colX = super.x;// - (width*.9 / 2);
        double colY = super.y;// - (height*.9 / 2);
        double colW = width;// * .9;
        double colH = height;// * .9;
        
        return new Rectangle2D.Double(colX, colY, colW, colH);
    }

    @Override
    public void addDead() {
        saucersDead++;
        saucersAlive--;
    }
    
    
    public void updateState(){
        if(sState instanceof SaucerStateAlive){
            if (direction > 0) {
                // moving to the right
                super.x += UNIT_TRAVEL;
                if (super.x + width/2 > GamePanel.width) {
                    direction = -1;
                }
            } else {
                // moving to the left
                super.x -= UNIT_TRAVEL;
                if (super.x - width/2 <= 0) {
                    direction = 1;
                }
            }
        } else if(sState instanceof SaucerStateDying){
            updateDirection();
            updateSize();
        }
    }

    private void updateDirection() {
        if(super.y > GamePanel.height){
            nextState();
        }
        super.y += 10;
    }

    private void updateSize() {
        width -= 2;
        height -= 1;
        if(height < 3){
            nextState();
        }
    }

    @Override
    public void powerUpCollected() {
        powerUpsCollected++;
    }

    public void setState(SaucerState state){
        sState = state;
    }
    
    @Override
    public void nextState() {
        if(sState instanceof SaucerStateDying){
            if(width < 3){
                sState.goNextState(this);
            }
        }else{
            sState.goNextState(this); 
        }
    }
}
