package model;

import java.util.ArrayList;

public interface Attackable {
    CanBeAttacked findNewTarget(ArrayList<CanBeAttacked> enemies);
    void attack(CanBeAttacked target);
}
