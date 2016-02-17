package model;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public abstract class GameFigure implements CollisionBox {

    // public for a faster access during animation
    public float x;
    public float y;
    
    public int state;
    public static final int STATE_ALIVE = 1;
    public static final int STATE_DYING = 2;
    public static final int STATE_DONE = 0;
    
    public int alliance;
    public static final int ALLIANCE_ENEMY = 0;
    public static final int ALLIANCE_FRIEND = 1;
    public static final int ALLIANCE_POWER = 2;

    public GameFigure(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // how to render on the canvas
    public abstract void render(Graphics2D g);

    // changes per frame
    public abstract void update();
    
    @Override
    public abstract Rectangle2D getCollisionBox();

    public abstract void addDead();
    
    public abstract void powerUpCollected();
    
    public abstract void nextState();

}
