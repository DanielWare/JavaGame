package model;

class PowerStateAlive implements PowerState {

    @Override
    public void goNextState(PowerUp context) {
       context.setState(new PowerStateUsed());
    }
    
}
