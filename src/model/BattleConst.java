package model;

public class BattleConst {
    public static final int MELEE_ATTACK_TYPE = 0;
    public static final int RANGED_ATTACK_TYPE = 1;
    public static final int GROUND_ATTACK_AREA = 1;

    public static final int AERIAL_ATTACK_AREA = 2;
    public static final int GROUND_AERIAL_ATTACK_AREA = 3;

    public static final String NONE_FAVOR_TARGET = "NONE";
    public static final String DEF_FAVOR_TARGET = "DEF";

    public static final int MOVING_SOLDIER_STATUS = 1;
    public static final int ATTACKING_SOLDIER_STATUS = 2;
    public static final int IDLE_SOLDIER_STATUS = 4;
    public static final int DEAD_SOLDIER_STATUS = 0;

    public static final double DISTANCE_EPSILON = 0.1;
    public static final double MOVE_SPEED_FACTOR_CONFIG = 0.1;
    public static final double TIME_STEP_SECOND = 0.02;

    public static final int SOLDIER_MAP_WIDTH = 43;
    public static final int SOLDIER_MAP_HEIGHT = 43;
    public static final int SOLDIER_X_START = -1;
    public static final int SOLDIER_Y_START = -1;

}
