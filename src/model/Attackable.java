package model;

import java.util.ArrayList;

public interface Attackable {
    CanBeAttacked findTarget(ArrayList<CanBeAttacked> enemies);
    void attack(CanBeAttacked target);
}
