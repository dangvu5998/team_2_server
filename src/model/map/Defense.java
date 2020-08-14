package model.map;
import model.BattleConst;
import model.battle.BattleSimulator;
import model.soldier.*;
import util.Common;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Defense extends Building {
    protected double minRange;
    protected double maxRange;
    protected double attackSpeed;
    protected double attackRadius;
    protected double attackArea;
    protected int attackType;
    protected double dmgPerShot;

    protected BattleSimulator battleModel;
    protected Soldier target;
    protected int timeStep;
    protected int lastAttackStep;
    protected double targetX;
    protected double targetY;
    protected short attackCharacter;
    protected String stringObjectType;

    public Defense(int id_, int x_, int y_, int mapObjectType_, int level_, int buildingStatus_, int finishTime_) {
        super(id_, x_, y_, mapObjectType_, level_, buildingStatus_, finishTime_);
    }

    public Defense(int id_, int x_, int y_, int mapObjectType_, int level_) {
        super(id_, x_, y_, mapObjectType_, level_);
    }

    public Defense(int id, int x, int y, int mapObjectType, int level, int mode) {
        super(id, x, y, mapObjectType, level, mode);
    }

    public void setBattleModel(BattleSimulator battleModel) {
        this.battleModel = battleModel;
        this.setTimeStep(battleModel.getTimeStep());
    }
    protected void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
        this.lastAttackStep = Integer.MIN_VALUE;
    }
    protected void setTargetPos(Soldier target) {
        this.targetX = target.getX();
        this.targetY = target.getY();
    }
    protected boolean canAttack() {
        final int minNextAttack = this.lastAttackStep + (int)Math.round(this.attackSpeed / BattleConst.TIME_STEP_SECOND);
        return this.timeStep >= minNextAttack;
    }
    protected boolean canAttackPos(double squareDistance) {
        final boolean isConstraintMin = this.minRange*this.minRange <= squareDistance;
        final boolean isConstraintMax = this.maxRange*this.maxRange >= squareDistance;
        return isConstraintMin && isConstraintMax;
    }
    private boolean isAlwaysHit() {
        return this.attackCharacter == BattleConst.DEFENSE_ATTACK_ALWAYS_HIT;
    }
    private double getSquareDistanceToTarget(double targetX, double targetY) {
        final double avr = (this.width + this.height) / 2.0;
        final double x = this.x + avr;
        final double y = this.y + avr;
        return Common.calcSquareDistance(x, y, targetX, targetY);
    }
    protected void findNewTarget(ArrayList<Soldier> targets) {
        this.target = null;
        double minHealth = Double.POSITIVE_INFINITY;
        for (Soldier target : targets) {
            if (!target.isAlive())
                continue;
            double squareDis = this.getSquareDistanceToTarget(target.getX(), target.getY());
            if (!this.canAttackPos(squareDis))
                continue;
            final double diff = target.getHealth() - minHealth;
            if (Math.abs(diff) < 0.1) {
                if (this.target != null && this.target.getId() < target.getId()) {
                    minHealth = target.getHealth();
                    this.target = target;
                }
            }
            else if (diff < 0) {
                minHealth = target.getHealth();
                this.target = target;
            }
        }
        if (this.target != null) {
            this.setTargetPos(this.target);
            this.lastAttackStep = this.timeStep;
            this.setStatusBattle(Building.BATTLE_STATUS_ATTACK);
            this.pushSoldiersQueue();
        }
    }
    public void update(ArrayList<Soldier> targets) {
        timeStep++;
        if (statusBattle == Building.BATTLE_STATUS_IDLE) {
            if (this.canAttack()) {
                this.findNewTarget(targets);
            }
        }
        else if (statusBattle == Building.BATTLE_STATUS_ATTACK) {
            this.attackTarget();
        }
    }
    public static class TargetInfo {
        private int typeAttack;
        private Soldier target;
        private double damage;
        private int lastAttackStep;
        private int nbTimeStep;
        private double targetX;
        private double targetY;
        private double radius;
        public boolean needDel;
        public TargetInfo (
                int typeAttack,
                Soldier target,
                double damage,
                int lastAttackStep,
                int nbTimeStep) {
            this.typeAttack = typeAttack;
            this.target = target;
            this.damage = damage;
            this.lastAttackStep = lastAttackStep;
            this.nbTimeStep = nbTimeStep;
            this.needDel = false;
        }
        public TargetInfo (
                int typeAttack,
                double targetX,
                double targetY,
                double damage,
                double radius,
                int lastAttackStep,
                int nbTimeStep) {
            this.typeAttack = typeAttack;
            this.damage = damage;
            this.lastAttackStep = lastAttackStep;
            this.nbTimeStep = nbTimeStep;
            this.targetX = targetX;
            this.targetY = targetY;
            this.radius = radius;
            this.needDel = false;
        }
        public int getTypeAttack() {
            return typeAttack;
        }

        public Soldier getTarget() {
            return target;
        }

        public double getDamage() {
            return damage;
        }

        public int getLastAttackStep() {
            return lastAttackStep;
        }

        public int getNbTimeStep() {
            return nbTimeStep;
        }

        public double getTargetX() {
            return targetX;
        }

        public double getTargetY() {
            return targetY;
        }

        public double getRadius() {
            return radius;
        }
    }
    protected void pushSoldiersQueue() {
        TargetInfo targetInfo;
        final int nbTimeStep = BattleConst.NUMBER_TIME_STEP_TO_HIT.get(this.stringObjectType);
        if (this.isAlwaysHit()) {
            targetInfo = new TargetInfo(
                    BattleConst.DEFENSE_ATTACK_ALWAYS_HIT,
                    this.target,
                    this.dmgPerShot,
                    this.lastAttackStep,
                    nbTimeStep
            );
        }
        else {
            targetInfo = new TargetInfo(
                    BattleConst.DEFENSE_ATTACK_CAN_MISS,
                    this.targetX,
                    this.targetY,
                    this.dmgPerShot,
                    this.attackRadius,
                    this.lastAttackStep,
                    nbTimeStep
            );
        }
        this.battleModel.pushSoldiersQueueAttack(targetInfo);
    }
    protected void attackTarget() {
        final int nbTimeStep = BattleConst.NUMBER_TIME_STEP_TO_HIT.get(this.stringObjectType);
        if (this.timeStep < this.lastAttackStep + nbTimeStep) {
            return;
        }
        this.setStatusBattle(Building.BATTLE_STATUS_IDLE);
    }
}
