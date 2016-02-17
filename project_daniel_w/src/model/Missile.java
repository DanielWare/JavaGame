package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Missile extends GameFigure{

    // missile size
    public static int SIZE = 5;//this needs to be cleaned
    private static final int MAX_EXPLOSION_SIZE = 50;
    private float dx; // displacement at each frame
    private float dy; // displacement at each frame

    // public properties for quick access
    public Color color;
    public Point2D.Float target;

    static final int UNIT_TRAVEL_DISTANCE = 4; // per frame move

    private int size;
    
    MissileState mState;
    
    /**
     *
     * @param sx start x of the missile
     * @param sy start y of the missile
     * @param tx target x of the missile
     * @param ty target y of the missile
     * @param color color of the missile
     * 
     */
    public Missile(float sx, float sy, float tx, float ty, Color color) {
        super(sx, sy);
        super.state = STATE_ALIVE;
        mState = new MissileStateAlive();
        super.alliance = ALLIANCE_FRIEND;
        target = new Point2D.Float(tx, ty);
        this.color = color;
        double angle = Math.atan2(Math.abs(ty - sy), Math.abs(tx - sx));
        dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
        dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));
        
        if (tx > sx && ty < sy) { // target is upper-right side
            dy = -dy; // dx > 0, dx < 0
        } else if (tx < sx && ty < sy) { // target is upper-left side
            dx = -dx;
            dy = -dy;
        } else if (tx < sx && ty > sy) { // target is lower-left side
            dx = -dx;
        } else { // target is lower-right side
            // dx > 0 , dy > 0
        }
        size = SIZE;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.drawOval((int) (super.x - size / 2),
                (int) (super.y - size / 2),
                size, size);
    }

    @Override
    public void update() {
        updateState();
        if (mState instanceof MissileStateAlive) {
            updateLocation();
        } else if (mState instanceof MissileStateExploding) {
            updateSize();
        }
    }

    public void updateLocation() {
        super.x += dx;
        super.y += dy;
    }

    public void updateSize() {
        size += 2;
    }

    private void updateState() {
        if (mState instanceof MissileStateAlive) {
            double distance = target.distance(super.x, super.y);
            boolean targetReached = distance <= 4.0;
            if (targetReached) {
                nextState();
            }
        } else if (mState instanceof MissileStateExploding) {
            if (size >= MAX_EXPLOSION_SIZE) {
                nextState();
            }
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        double colX = super.x - (size*.9 / 2);
        double colY = super.y - (size*.9 / 2);
        double colW = size * .9;
        double colH = size * .9;
        
        return new Rectangle2D.Double(colX, colY, colW, colH);
    }

    @Override
    public void addDead() {
        //not used
    }

    @Override
    public void powerUpCollected() {
        //not used
    }

    public void setState(MissileState state){
        mState = state;
    }
    
    @Override
    public void nextState() {
        if(mState instanceof MissileStateExploding){
            if(size >= MAX_EXPLOSION_SIZE){
                mState.goNextState(this);
            }
        } else {
            mState.goNextState(this);
        }
    }

}
