
package model;

public class LaserWeapon extends WeaponPower{

    public LaserWeapon(WeaponComponent weapon) {
        super(weapon);
    }

    @Override
    public void shoot(int sx, int sy, int tx, int ty) {
        super.shoot(sx, sy, tx, ty);
        laserShoot(sx, sy, tx, ty);
    }
    
    public void laserShoot(int sx, int sy, int tx, int ty){
                controller.Main.gameData.gameFigures.add(new Laser(sx, sy, tx, ty));

    }
    
}
