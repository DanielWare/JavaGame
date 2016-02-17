package model;

public abstract class WeaponPower implements WeaponComponent{
    
    protected WeaponComponent weapon;
    
    public WeaponPower(WeaponComponent weapon){
        this.weapon = weapon;
    }
    
    @Override
    public void shoot(int sx, int sy, int tx, int ty){
        weapon.shoot(sx, sy, tx, ty);
    }
    
}