
package model;

import java.awt.Color;

public class BasicWeapon implements WeaponComponent {

    @Override
    public void shoot(int sx, int sy, int tx, int ty) {
        controller.Main.gameData.gameFigures.add(new Missile(sx, sy, tx, ty, Color.BLUE));
    }
    
}
