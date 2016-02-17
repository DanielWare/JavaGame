package model;

class LaserStateDecay implements LaserState {

    @Override
    public void goNextState(Laser context) {
        context.setState(new LaserStateDone());
        context.state = GameFigure.STATE_DONE;
    }

}
