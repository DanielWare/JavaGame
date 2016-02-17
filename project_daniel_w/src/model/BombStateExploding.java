package model;

public class BombStateExploding implements BombState {

    @Override
    public void goNextState(Bomb context) {
       context.setState(new BombStateDone());
       context.state = GameFigure.STATE_DONE;        
    }

    
}