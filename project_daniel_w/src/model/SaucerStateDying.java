package model;

class SaucerStateDying implements SaucerState {

    @Override
    public void goNextState(FlyingSaucer context) {
        context.setState(new SaucerStateDone());
        context.state = GameFigure.STATE_DONE;
    }


    
}
