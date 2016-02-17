
package model;

public class MissileStateExploding implements MissileState {

    @Override
    public void goNextState(Missile context) {
        context.setState(new MissileStateDone());
        context.state = GameFigure.STATE_DONE;
 
     }
    
}
