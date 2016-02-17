package model;

class PowerStateUsed implements PowerState {

    @Override
    public void goNextState(PowerUp context) {
       context.setState(new PowerStateDone());
       context.state = GameFigure.STATE_DONE;
    }

}
