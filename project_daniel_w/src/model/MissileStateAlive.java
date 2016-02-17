package model;

public class MissileStateAlive implements MissileState {
    
    @Override
    public void goNextState(Missile context) {
        context.setState(new MissileStateExploding());
    }
    
}
