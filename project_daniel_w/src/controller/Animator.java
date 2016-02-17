package controller;

import java.util.ArrayList;
import java.util.List;
import model.FlyingSaucer;
import model.GameFigure;
import model.PowerUp;
import model.Shooter;
import view.MainWindow;

public class Animator implements Runnable {

    public boolean running = true;
    private final int FRAMES_PER_SECOND = 20;

    @Override
    public void run() {

        while (running) {
            long startTime = System.currentTimeMillis();

            processCollisions();
            
            Main.gameData.update();
            Main.gamePanel.gameRender();
            Main.gamePanel.printScreen();

            long endTime = System.currentTimeMillis();
            int sleepTime = (int) (1.0 / FRAMES_PER_SECOND * 1000)
                    - (int) (endTime - startTime);

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime); // ms
                } catch (InterruptedException e) {

                }
            }
        }
        System.exit(0);
    }
    
    private synchronized void processCollisions() {
        
        List<GameFigure> returnObjects = new ArrayList();
        for (int i = 0; i < Main.gameData.gameFigures.size(); i++) {
            GameFigure figure = Main.gameData.gameFigures.get(i);
            
            returnObjects.clear();
            Main.gameData.quadtree.retrieve(returnObjects, figure);
            for (GameFigure returnObject : returnObjects) {

                // Run collision detection algorithm between objects
                if (returnObject.getCollisionBox().intersects(figure.getCollisionBox())) {
                    //objects with same alliance cannot collide
                    if (figure.alliance != returnObject.alliance) {
                        //enemy and friend collide
                        if(!(figure instanceof PowerUp) && !(returnObject instanceof PowerUp)){
                            returnObject.nextState();
                        }
                        //friend and powerup collide
                        else if(figure.alliance != GameFigure.ALLIANCE_ENEMY && returnObject.alliance != GameFigure.ALLIANCE_ENEMY){
                            //shooter eats powerUp's
                            if(figure instanceof Shooter){
                                returnObject.nextState();
                                Shooter.powerUpsCollected++;
                            }
//                            } else if(returnObject instanceof Shooter){
//                                figure.nextState();
//                                Shooter.powerUpsCollected++;
//                            }
                        }
                        //enemy and powerup
                        else if(figure.alliance != GameFigure.ALLIANCE_FRIEND && returnObject.alliance != GameFigure.ALLIANCE_FRIEND){
                            //enemy eats powerUp's
                            if(figure.alliance == GameFigure.ALLIANCE_ENEMY){
                                returnObject.nextState();
                                figure.powerUpCollected();
                            }
//                            } else if(returnObject.alliance == GameFigure.ALLIANCE_ENEMY){
//                                figure.nextState();
//                                returnObject.powerUpCollected();
//                            }
                        }
                    }
                }
            }
        }
        
        
        
        //Score Update
        MainWindow.enemyStats.setText("UFOs destroyed: " + FlyingSaucer.saucersDead + "     Power Up's Collected: " + Shooter.powerUpsCollected);
        MainWindow.powerStats.setText("Score: " + (Shooter.powerUpsCollected + FlyingSaucer.saucersDead *5));
        
    }

}
