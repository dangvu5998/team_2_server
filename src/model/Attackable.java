package model;

import java.util.ArrayList;

public interface Attackable {
    CanBeAttacked findNewTarget(ArrayList<? extends CanBeAttacked> enemies);
    void attack(CanBeAttacked target);
}
