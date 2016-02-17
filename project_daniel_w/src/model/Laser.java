package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Laser extends GameFigure{

    public Color color;
    public Point2D.Float direction;
    LaserState lState;
    private final int DECAY_RATE = 5;
    private int decayCount;
    
    public Laser(float sx, float sy, float tx, float ty) {
        super(sx, sy);
        super.state = STATE_ALIVE;
        super.alliance = ALLIANCE_FRIEND;
        decayCount = 0;
        lState = new LaserStateAlive();
        direction = new Point2D.Float(tx, ty);
        this.color = Color.RED;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.drawLine((int)super.x, (int)super.y, (int)direction.getX(), (int)direction.getY());
    }

    @Override
    public void update() {
        decayCount++;
        if(decayCount > DECAY_RATE){
            nextState();
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(direction.getX(), direction.getY(), 1, 1);
    }

    @Override
    public void addDead() {
        //not used
    }

    @Override
    public void powerUpCollected() {
        //not used
    }

    @Override
    public void nextState() {
        if(lState instanceof LaserStateDecay){
            if(decayCount > DECAY_RATE){
                lState.goNextState(this);
            }
        }else{
            lState.goNextState(this);
        }
    }
    
    public void setState(LaserState state){
        lState = state;
    }
    
}