
package model;


public class LaserStateAlive implements LaserState {

    @Override
    public void goNextState(Laser context) {
        context.setState(new LaserStateDecay());
    }
    
}
