package model.soldier;

import model.Attackable;
import model.BattleConst;
import model.CanBeAttacked;
import model.map.Defense;
import util.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class Soldier implements Attackable, CanBeAttacked {
    protected int attackType;
    protected int attackArea;
    protected double moveSpeed;
    protected double attackSpeed;
    protected double attackRange;
    protected String favoriteTarget;
    protected double dmgPerAtk;
    protected double x;
    protected double y;
    protected double health;
    protected CanBeAttacked attackTarget;

//    public static final String[] SOLDIER_TYPES = new String[]
    public static final Set<String> SOLDIER_TYPES = new HashSet<>(Arrays.asList
        (
            "ARM_1",
            "ARM_2",
            "ARM_4"));

    public boolean isFavoriteTarget(Object obj) {
        if(favoriteTarget.equals(BattleConst.NONE_FAVOR_TARGET)) {
            return true;
        }
        if(favoriteTarget.equals(BattleConst.DEF_FAVOR_TARGET)) {
            return obj instanceof Defense;
        }
        return false;
    }

    @Override
    public CanBeAttacked findNewTarget(ArrayList<CanBeAttacked> canBeAttackeds) {
        CanBeAttacked res = null;
        double bestDistance = Double.MAX_VALUE;
        boolean isFavorBest = false;
        for(CanBeAttacked canBeAttacked: canBeAttackeds) {
            if(!canBeAttacked.isAlive()) {
                continue;
            }
            boolean isFavorCurrTarget = isFavoriteTarget(canBeAttacked);
            double currDistance = Common.calcSquareDistance(x, y, canBeAttacked.getBattleX(), canBeAttacked.getBattleY());
            if(res == null) {
                res = canBeAttacked;
                bestDistance = Common.calcSquareDistance(x, y, res.getBattleX(), res.getBattleY());
                isFavorBest = isFavorCurrTarget;
                continue;
            }
            if(isFavorBest && !isFavorCurrTarget) {
                continue;
            }
            if(!isFavorBest && isFavorCurrTarget) {
                res = canBeAttacked;
                isFavorBest = true;
                bestDistance = currDistance;
                continue;
            }
            if(bestDistance > currDistance) {
                res = canBeAttacked;
                bestDistance = currDistance;
            }
        }
        return res;
    }

    @Override
    public double getBattleX() {
        return x;
    }

    @Override
    public double getBattleY() {
        return y;
    }

    @Override
    public void attack(CanBeAttacked target) {
        target.takeDamage(dmgPerAtk);
    }

    @Override
    public void takeDamage(double dmg) {
        health -= dmg;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }
}
