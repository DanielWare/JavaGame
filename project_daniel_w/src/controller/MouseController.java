package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static model.GameData.weapon;
import model.Shooter;

public class MouseController extends MouseAdapter {

    private int px;
    private int py;

    @Override
    public void mousePressed(MouseEvent me) {
        px = me.getX();
        py = me.getY();

        Shooter shooter = (Shooter) Main.gameData.gameFigures.get(0);

//        Missile m = new Missile(
//                shooter.getXofMissileShoot(),
//                shooter.getYofMissileShoot(),
//                px, py, // target location where the missile explodes
//                Color.RED);

        float sx = shooter.getXofMissileShoot();
        float sy = shooter.getYofMissileShoot();
        
        weapon.shoot((int)sx, (int)sy, px, py);

//        synchronized (Main.gameData.gameFigures) {
//            //Main.gameData.gameFigures.add(m);
////            for(int j = 0; j < gf.size(); j++){
////                System.out.println(gf.get(j).toString());
////            }
//            for(int i = 0; i < gf.length - 1; i++){
//                Main.gameData.gameFigures.add(gf[i]);
//            }
//            
//        }
    }

}
