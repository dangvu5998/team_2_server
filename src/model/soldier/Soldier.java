package model.soldier;

import model.Attackable;
import model.BattleConst;
import model.CanBeAttacked;
import model.map.Defense;
import util.Common;

import java.util.ArrayList;

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

    public static boolean checkTargetType(Object obj, String targetType) {
        if(targetType.equals(BattleConst.NONE_FAVOR_TARGET)) {
            return true;
        }
        if(targetType.equals(BattleConst.DEF_FAVOR_TARGET)) {
            return obj instanceof Defense;
        }
        return false;
    }

    public CanBeAttacked findTarget(ArrayList<CanBeAttacked> canBeAttackeds) {
        CanBeAttacked res = null;
        double bestDistance = Double.MAX_VALUE;
        boolean bestTrueType = false;
        for(CanBeAttacked canBeAttacked: canBeAttackeds) {
            if(res == null) {
                res = canBeAttacked;
                bestDistance = Common.calcSquareDistance(x, y, res.getBattleX(), res.getBattleY());
                if(checkTargetType(canBeAttacked, favoriteTarget)) {
                    bestTrueType = true;
                }
                continue;
            }
            boolean checkTypeTarget = checkTargetType(canBeAttacked, favoriteTarget);
            if(bestTrueType && !checkTypeTarget) {
                continue;
            }
            double distance = Common.calcSquareDistance(x, y, canBeAttacked.getBattleX(), canBeAttacked.getBattleY());
            if(!bestTrueType && checkTypeTarget) {
                res = canBeAttacked;
                bestTrueType = true;
                bestDistance = distance;
                continue;
            }
            if(bestDistance > distance) {
                res = canBeAttacked;
                bestDistance = distance;
            }
        }
        return res;
    };

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
}
