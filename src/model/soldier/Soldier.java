package model.soldier;

import model.Attackable;
import model.BattleConst;
import model.CanBeAttacked;
import model.map.Defense;
import model.map.Wall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class Soldier implements Attackable, CanBeAttacked {
    public static final Logger logger = LoggerFactory.getLogger("Soldier");
    protected int id;
    protected String type;
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
    protected CanBeAttacked target;
    protected double targetPosX = -1;
    protected double targetPosY = -1;
    // list of coordinates to move
    // each element is an array size 2, element 0 is x to move
    // element 1 is y to move
    protected ArrayList<double[]> path;
    protected int pathIndex;
    protected int status = BattleConst.IDLE_SOLDIER_STATUS;
    protected int lastAttackStep;
    protected int timeStep;

    public static final Set<String> SOLDIER_TYPES = new HashSet<>(Arrays.asList
        (
            "ARM_1",
            "ARM_2",
            "ARM_4"));

    public void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
        this.lastAttackStep = timeStep;
    }

    public boolean isFavoriteTarget(Object obj) {
        if(favoriteTarget.equals(BattleConst.DEF_FAVOR_TARGET)) {
            return obj instanceof Defense;
        }
        return false;
    }

    public void setPath(ArrayList<double[]> path) {
        this.pathIndex = 0;
        this.path = path;
        this.targetPosX = path.get(path.size() - 1)[0];
        this.targetPosY = path.get(path.size() - 1)[1];
    }

    public void move() {
        if(path == null || path.size() == 0 || pathIndex >= path.size()) {
            return;
        }
        double[] coorToMove = path.get(pathIndex);
        double xToMove = coorToMove[0];
        double yToMove = coorToMove[1];
        double distanceToNextNode = Common.calcEuclideanDistance(xToMove, yToMove, x, y);
        if(distanceToNextNode < BattleConst.DISTANCE_EPSILON) {
            pathIndex += 1;
            move();
            return;
        }
        double unitDirectX = (xToMove - x) / distanceToNextNode;
        double unitDirectY = (yToMove - y) / distanceToNextNode;
        double velocity = moveSpeed * BattleConst.TIME_STEP_SECOND;

        x += velocity * unitDirectX;
        y += velocity * unitDirectY;
    }

    @Override
    public CanBeAttacked findNewTarget(ArrayList<? extends CanBeAttacked> canBeAttackeds) {
        target = null;
        double bestDistance = Double.MAX_VALUE;
        boolean isFavorBest = false;
        for(CanBeAttacked canBeAttacked: canBeAttackeds) {
            if(!canBeAttacked.isAlive()) {
                continue;
            }
            if(canBeAttacked instanceof Wall) {
                continue;
            }
            boolean isFavorCurrTarget = isFavoriteTarget(canBeAttacked);
            double currDistance = Common.calcSquareDistance(x, y, canBeAttacked.getBattleX(), canBeAttacked.getBattleY());
            if(target == null) {
                target = canBeAttacked;
                bestDistance = currDistance;
                isFavorBest = isFavorCurrTarget;
                continue;
            }
            if(isFavorBest && !isFavorCurrTarget) {
                continue;
            }
            if(!isFavorBest && isFavorCurrTarget) {
                target = canBeAttacked;
                isFavorBest = true;
                bestDistance = currDistance;
                continue;
            }
            if((Math.abs(Math.round(bestDistance - currDistance)) < 0.1 && canBeAttacked.getId() < target.getId()) || (bestDistance > currDistance + 0.1)) {
                target = canBeAttacked;
                bestDistance = currDistance;
            }
        }
        return target;
    }

    public void updateStatus() {
        timeStep += 1;
        if(target == null || !target.isAlive()) {
            status = BattleConst.IDLE_SOLDIER_STATUS;
            targetPosX = -1;
            targetPosY = -1;
        }
        else if(Common.calcSquareDistance(x, y, targetPosX, targetPosY) < BattleConst.DISTANCE_EPSILON) {
            status = BattleConst.ATTACKING_SOLDIER_STATUS;
        }
        else {
            status = BattleConst.MOVING_SOLDIER_STATUS;
        }
    }

    public void attackTarget() {
        int minNextAttackStep = lastAttackStep + (int) Math.round(attackSpeed / BattleConst.TIME_STEP_SECOND);
        if(timeStep >= minNextAttackStep) {
            lastAttackStep = timeStep;
            target.takeDamage(dmgPerAtk);
        }
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
        if(health > 0) {
            health -= dmg;
            if(health < 0) {
                health = 0;
            }
        }
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTarget(CanBeAttacked target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public int getAttackType() {
        return attackType;
    }

    public double getAttackRange() {
        return attackRange;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int getId() {
        return id;
    }

    public ArrayList<double[]> getPath() {
        return path;
    }

    public CanBeAttacked getTarget() {
        return target;
    }

    public double getHealth() {
        return health;
    }

    public double getTargetPosX() {
        return targetPosX;
    }

    public double getTargetPosY() {
        return targetPosY;
    }
}
