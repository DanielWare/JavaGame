package model;

import static controller.MouseMovementListener.mouseX;
import static controller.MouseMovementListener.mouseY;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Shooter extends GameFigure {

    private double rotation;
    private Image launcherImage;
    private int speed = 5;
    public static int powerUpsCollected = 0;
    private final int POWER_UP_THRESHOLD = 10;
    private int previousPwrUps;
    private int weaponPower = 0;
    
    public Shooter(int x, int y) {
        super(x, y);
        super.state = STATE_ALIVE;
        super.alliance = ALLIANCE_FRIEND;
        previousPwrUps = powerUpsCollected;
        launcherImage = null;

        try {
            launcherImage = ImageIO.read(getClass().getResource("shooter.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {
        
        rotation = Math.atan2((mouseX - (super.x + 35)), - (mouseY - (super.y + 35)));
        AffineTransform at = AffineTransform.getTranslateInstance(super.x, super.y);
        at.rotate(rotation, 35, 40);
        
        g.drawImage(launcherImage, at, null);
    }

    @Override
    public void update() {
        if(powerUpsCollected > previousPwrUps && powerUpsCollected % POWER_UP_THRESHOLD ==0){
            weaponPower++;
            if(weaponPower == 1){
                GameData.weapon = new LaserWeapon(GameData.weapon);
                weaponPower++;
            }
        }
        previousPwrUps = powerUpsCollected;
    }

    public void translate(int dx, int dy) {

        
        super.x += (dx * speed);
        super.y += (dy * speed);
    }
    
    // Missile shoot location: adjust x and y to the image
    public float getXofMissileShoot() {
        return (float) (super.x + (35) + Math.sin(rotation)*(35));
    }
    
    public float getYofMissileShoot() {
        return (float)(super.y + (35) - Math.cos(rotation) * (35));
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(super.x, super.y, 70, 70);
    }

    @Override
    public void addDead() {
        //not used
    }

    @Override
    public void powerUpCollected() {
        powerUpsCollected++;
    }

    @Override
    public void nextState() {
        //not used
    }
    
}
