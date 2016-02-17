package model;


class BombStateAlive implements BombState {

    @Override
    public void goNextState(Bomb context) {
        context.setState(new BombStateExploding());
    }
    
}
