package model;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import view.GamePanel;

public class Bomb extends GameFigure {

    public static int bombsAlive;
    public static int bombsDead;
    private int dy = 3;
    private int dx = 3;
    private int width = 20;
    private int height = 20;

    private BufferedImage bombImage;
    private Point spriteCoordinateTop;   
    private Point spriteCoordinateBottom;

    private int powerUpsCollected = 0;
    private final int POWER_UP_THRESHOLD = 1;
    private final int MAX_BOMB_SIZE = 70;
    private int moveCount = 0;
    BombState bState;
    
    public Bomb(float x, float y) {
        super(x, y);
        bombsAlive++;
        super.state = STATE_ALIVE;
        super.alliance = ALLIANCE_ENEMY;
        bState = new BombStateAlive();
        spriteCoordinateTop = new Point(0,0);
        spriteCoordinateBottom = new Point(16, 16);
        
        try{
            bombImage = ImageIO.read(getClass().getResource("bombas.png"));
        } catch (IOException ex){
            JOptionPane.showMessageDialog(null, "Error: Cannot open bomb.png");
            System.exit(-1);
        }
        
        
    }

    @Override
    public void render(Graphics2D g) {

        g.drawImage(bombImage, (int)super.x, (int)super.y, (int)(super.x + width), (int)(super.y + height),
                spriteCoordinateTop.x, spriteCoordinateTop.y, spriteCoordinateBottom.x, spriteCoordinateBottom.y, null);
    }

    @Override
    public void update() {
        moveCount++;
        if(super.y > GamePanel.height + 10){
            nextState();
        } else {
            updateState();
        }
        super.y += dy;
        if(moveCount % 20 == 0){
            dx = -dx;
        }
        super.x += dx;
        if(powerUpsCollected > 0 && powerUpsCollected % POWER_UP_THRESHOLD == 0 && bState instanceof BombStateAlive){
            powerUpsCollected = 0;
            if(width < MAX_BOMB_SIZE){
                width+= 10;
                height+= 10;
            }
            
        }
            
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(super.x, super.y, width, height);
    }

    @Override
    public void addDead() {
        bombsDead++;
        bombsAlive--;
    }

    private void updateState() {
        if(bState instanceof BombStateExploding){
            spriteCoordinateTop = new Point(16, 0);
            spriteCoordinateBottom = new Point(64, 48);
            updateSize();
        }
    }

    private void updateSize() {
        width --;
        height --;
        if(width < 8){
            nextState();
        } else if(width < 15){
            spriteCoordinateTop = new Point(71, 5);
            spriteCoordinateBottom = new Point(105, 42);
        }
    }
    
    public void powerUpCollected(){
        powerUpsCollected++;
    }

    public void setState(BombState state){
        bState = state;
    }
    
    @Override
    public void nextState() {
        if(bState instanceof BombStateExploding){
            if(width < 2 || super.y > GamePanel.height + 10){
                bState.goNextState(this);
            }
        } else{
            bState.goNextState(this);
        }
    }
    
}
