package model;

public interface CanBeAttacked extends BattleObject {
    void takeDamage(double dmg);
    boolean isAlive();
}
