package model;

import controller.Main;
import controller.Quadtree;
import view.GamePanel;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameData {

    private final int RADIUS = 6;
    public static Shooter shooter;

    public static WeaponComponent weapon;
    
    public final List<GameFigure> gameFigures;
    public final Quadtree quadtree;
    
    private boolean started = false;
    private final int MAX_SAUCERS = 7;
    private final int MAX_POWERUPS = 10;
    private final int MAX_BOMBS = 12;
    
    
    public static Background bg;
    
    public GameData() {
        gameFigures = Collections.synchronizedList( new ArrayList<GameFigure>() );
        shooter = new Shooter(Main.WIN_WIDTH / 2, Main.WIN_HEIGHT / 2);

        gameFigures.add(shooter);
        weapon = new BasicWeapon();
        
        quadtree = new Quadtree(1, new Rectangle2D.Double(0, 0, Main.WIN_WIDTH, Main.WIN_HEIGHT));
        
        bg = new Background();
        
    }

    public void start(){
        while(PowerUp.powerAlive < MAX_POWERUPS){
            addPower();
        }
        while(Bomb.bombsAlive < MAX_BOMBS){
            addBomb();
        }
        while(FlyingSaucer.saucersAlive < MAX_SAUCERS){
            addSaucer();
        }
        started = true;
    }
    
    public void addPower() {
        synchronized (gameFigures) {
                float red = (float) Math.random();
                float green = (float) Math.random();
                float blue = (float) Math.random();
                // adjust if too dark since the background is black
                if (red < 0.5) {
                    red += 0.2;
                }
                if (green < 0.5) {
                    green += 0.2;
                }
                if (blue < 0.5) {
                    blue += 0.2;
                }
                gameFigures.add(new PowerUp(
                        (int) (Math.random() * GamePanel.width),
                        (int) (Math.random() * GamePanel.height),
                        RADIUS,
                        new Color(red, green, blue)));
        }
    }
    
    public void addSaucer() {
        synchronized (gameFigures){
            gameFigures.add(new FlyingSaucer((int) (Math.random() * GamePanel.width), (int) (Math.random() * (GamePanel.height / 2))));
        }
    }

    public void addBomb() {
        synchronized(gameFigures){
            gameFigures.add(new Bomb((float) (Math.random() * GamePanel.width), -5.0f));

        }
    }
    
    public void update() {
        bg.update();
        synchronized (gameFigures) {
            ArrayList<GameFigure> remove = new ArrayList<>();
            GameFigure f;
            for (int i = 0; i < gameFigures.size(); i++) {
                f = gameFigures.get(i);
                if (f.state == GameFigure.STATE_DONE) {
                    remove.add(f);
                }
            }
            
            for(int i = 0; i < remove.size(); i++){
                remove.get(i).addDead();
            }
            gameFigures.removeAll(remove);

            quadtree.clear();
            if(started){
                start();
            }
            
            for (GameFigure g : gameFigures) {
                g.update();
                quadtree.insert(g);
            }
        }
    }
}
