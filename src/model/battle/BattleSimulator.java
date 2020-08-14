package model.battle;

import model.BattleConst;
import model.CanBeAttacked;
import model.map.Building;
import model.map.MapObject;
import model.map.Wall;
import model.soldier.Soldier;
import model.soldier.Warrior;
import model.soldier.Archer;
import model.soldier.Giant;
import util.Common;

import java.util.*;
import java.util.stream.Collectors;

public class BattleSimulator {
    private final Queue<BattleSession.DropSoldier> dropSoldiers;
    private int timestep;
    private final ArrayList<Soldier> aliveSoldiers;
    private final ArrayList<Building> aliveBuildings;
    private final ArrayList<SoldierTargetPath> soldierTargetPaths;
    private final ArrayList<Wall> aliveWallBuildings;
    private final ArrayList<BreakingWall> breakingWalls;
    private final MapBattleGraph mapGraph;
    private int idCounter;
    private final Common.LinearCongruentialGenerator lcg;

    private final StringBuilder stateLogger;
    public boolean debug = true;

    static class BreakingWall {
        private final Building buildingTarget;
        private final Wall wall;

        public BreakingWall(Wall wall, Building buildingTarget) {
            this.wall = wall;
            this.buildingTarget = buildingTarget;
        }

        public Building getBuildingTarget() {
            return buildingTarget;
        }
    }

    static class SoldierTargetPath {
        private final String soldierType;
        // this is coordinate, not real position
        private final int posX;
        private final int posY;
        private final ArrayList<int[]> path;
        private final Building target;
        private final double cornerTargetPosX;
        private final double cornerTargetPosY;

        public SoldierTargetPath(String soldierType, int posX, int posY,
                                 ArrayList<int[]> path, Building target,
                                 double cornerTargetPosX, double cornerTargetPosY) {
            this.soldierType = soldierType;
            this.posX = posX;
            this.posY = posY;
            this.path = path;
            this.target = target;
            this.cornerTargetPosX = cornerTargetPosX;
            this.cornerTargetPosY = cornerTargetPosY;
        }

        public String getSoldierType() {
            return soldierType;
        }

        public int getPosX() {
            return posX;
        }

        public int getPosY() {
            return posY;
        }

        public ArrayList<int[]> getPath() {
            return path;
        }

        public Building getTarget() {
            return target;
        }

        public double getCornerTargetPosX() {
            return cornerTargetPosX;
        }

        public double getCornerTargetPosY() {
            return cornerTargetPosY;
        }
    }

    public BattleSimulator(ArrayList<MapObject> battleMapObjects, ArrayList<BattleSession.DropSoldier> dropSoldiers) {
        // TODO: add check fighting ended
        mapGraph = new MapBattleGraph(BattleConst.SOLDIER_MAP_WIDTH, BattleConst.SOLDIER_MAP_HEIGHT,
                BattleConst.SOLDIER_X_START, BattleConst.SOLDIER_Y_START);
        mapGraph.loadGraphFromMapObjs(battleMapObjects);
        aliveWallBuildings = new ArrayList<>();
        aliveBuildings = new ArrayList<>();
        breakingWalls = new ArrayList<>();
        soldierTargetPaths = new ArrayList<>();
        for(MapObject mapObject: battleMapObjects) {
            addMapObj(mapObject);
        }
        aliveBuildings.sort(Comparator.comparingInt(MapObject::getId));
        aliveWallBuildings.sort(Comparator.comparingInt(MapObject::getId));
        dropSoldiers.sort(Comparator.comparingInt(BattleSession.DropSoldier::getTimestep));
        this.dropSoldiers = new LinkedList<>(dropSoldiers);
        lcg = new Common.LinearCongruentialGenerator(2333, 8121, 28411, 134457);
        aliveSoldiers = new ArrayList<>();
        idCounter = 0;

        // debug
        stateLogger = new StringBuilder();
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    private void addMapObj(MapObject mapObject) {
        if(mapObject instanceof Building) {
            if(!(mapObject instanceof Wall)) {
                aliveBuildings.add((Building) mapObject);
            }
            else {
                aliveWallBuildings.add((Wall) mapObject);
            }
        }
    }

    private int getNewId() {
        idCounter += 1;
        return idCounter;
    }

    public void simulate(int totalTimestep) {
        for(int i = 0; i < totalTimestep; i++) {
            forwardStep();
        }
    }

    private void addSoldier(String soldierType, int x, int y) {
        Soldier newSoldier = null;
        switch (soldierType) {
            case Warrior.TYPE_ID:
                newSoldier = new Warrior(getNewId(), x, y, 1);
                break;
            case Archer.TYPE_ID:
                newSoldier = new Archer(getNewId(), x, y, 1);
                break;
            case Giant.TYPE_ID:
                newSoldier = new Giant(getNewId(), x, y, 1);
                break;
        }
        if(newSoldier == null) {
            return;
        }
        newSoldier.setTimeStep(timestep);
        aliveSoldiers.add(newSoldier);
    }

    private int[][] getCornersOfMapObj(MapObject mapObj) {
        return new int[][]{
                {mapObj.getX(), mapObj.getY()},
                {mapObj.getX() + mapObj.getWidth(), mapObj.getY()},
                {mapObj.getX(), mapObj.getY() + mapObj.getHeight()},
                {mapObj.getX() + mapObj.getWidth(), mapObj.getY() + mapObj.getHeight()},
        };
    }

    private Wall findWallToBreak(Soldier soldier, Building buildingTarget) {
        final int DISTANCE_TH = 5;
        for(BreakingWall breakingWall: breakingWalls) {
            if(breakingWall.getBuildingTarget() == buildingTarget
                    && Common.calcGridDistance(buildingTarget.getX(), buildingTarget.getY(), soldier.getBattleX(), soldier.getBattleY()) + DISTANCE_TH
                    > Common.calcGridDistance(breakingWall.wall.getX(), breakingWall.wall.getY(), buildingTarget.getX(), buildingTarget.getY())
                    + Common.calcGridDistance(breakingWall.wall.getX(), breakingWall.wall.getY(), soldier.getBattleX(), soldier.getBattleY())
            ) {
                return breakingWall.wall;
            }
        }
        if(aliveWallBuildings.size() == 0) {
            return null;
        }
        Wall wallToBreak = aliveWallBuildings.get(0);
        double distanceToWall = Common.calcGridDistance(wallToBreak.getX(), wallToBreak.getY(), soldier.getBattleX(), soldier.getBattleY());
        double distanceToMove = distanceToWall + Common.calcGridDistance(wallToBreak.getX(), wallToBreak.getY(), buildingTarget.getX(), buildingTarget.getY());
        for(int i = 1; i < aliveWallBuildings.size(); i++) {
            Wall currWall = aliveWallBuildings.get(i);
            double currDistanceToWall = Common.calcGridDistance(currWall.getX(), currWall.getY(), soldier.getBattleX(), soldier.getBattleY());
            double currDistanceToMove = currDistanceToWall + Common.calcGridDistance(currWall.getX(), currWall.getY(), buildingTarget.getX(), buildingTarget.getY());
            if(currDistanceToMove < distanceToMove ||
                    (Math.abs(currDistanceToMove - distanceToMove) < BattleConst.DISTANCE_EPSILON && currDistanceToWall < distanceToWall)
            ) {
                distanceToMove = currDistanceToMove;
                distanceToWall = currDistanceToWall;
                wallToBreak = currWall;
            }
        }
        breakingWalls.add(new BreakingWall(wallToBreak, buildingTarget));
        return wallToBreak;
    }

    private void simulateSoldiers() {
        // handle soldier find path, move and attack buildings
        for(Soldier soldier: aliveSoldiers) {
            soldier.updateStatus();
            if(soldier.getStatus() == BattleConst.IDLE_SOLDIER_STATUS) {
                double soldierPosX = soldier.getX();
                double soldierPosY = soldier.getY();
                int roundedSoldPosX = (int) Math.round(soldierPosX);
                int roundedSoldPosY = (int) Math.round(soldierPosY);
                double targetPosX, targetPosY;
                Building target;
                ArrayList<int[]> pathToTarget = null;

                SoldierTargetPath targetPathPreCalc = null;
                for(SoldierTargetPath soldierTargetPath: soldierTargetPaths) {
                    if(soldier.getType().equals(soldierTargetPath.getSoldierType())) {
                        if (roundedSoldPosX == soldierTargetPath.getPosX() && roundedSoldPosY == soldierTargetPath.getPosY()) {
                            targetPathPreCalc = soldierTargetPath;
                            break;
                        }
                        double centerTargetX = soldierTargetPath.getTarget().getX() + soldierTargetPath.getTarget().getWidth() / 2.0;
                        double centerTargetY = soldierTargetPath.getTarget().getY() + soldierTargetPath.getTarget().getHeight() / 2.0;
                        if(mapGraph.hasPathCoord(roundedSoldPosX, roundedSoldPosY, soldierTargetPath.getPosX(), soldierTargetPath.getPosY())
                                && (Common.calcManhattanDistance(roundedSoldPosX, roundedSoldPosY, centerTargetX, centerTargetY) -
                                Common.calcManhattanDistance(soldierTargetPath.getPosX(), soldierTargetPath.getPosY(), centerTargetX, centerTargetY) > BattleConst.DISTANCE_EPSILON)) {
                            targetPathPreCalc = soldierTargetPath;
                            break;
                        }
                    }
                }
                if(targetPathPreCalc != null) {
                    target = (Building) targetPathPreCalc.getTarget();
                    soldier.setTarget(target);
                    pathToTarget = new ArrayList<>(targetPathPreCalc.getPath());
                    targetPosX = targetPathPreCalc.getCornerTargetPosX();
                    targetPosY = targetPathPreCalc.getCornerTargetPosY();
                }
                else {
                    CanBeAttacked canBeAttacked = soldier.findNewTarget(aliveBuildings);
                    if(canBeAttacked == null) {
                        break;
                    }
                    target = (Building) canBeAttacked;
                    // find target point for soldier
                    targetPosX = target.getX();
                    targetPosY = target.getY();
                    for (int[] cornerPos : getCornersOfMapObj(target)) {
                        if (Common.calcGridDistance(soldierPosX, soldierPosY, targetPosX, targetPosY) -
                                Common.calcGridDistance(soldierPosX, soldierPosY, cornerPos[0], cornerPos[1]) > BattleConst.DISTANCE_EPSILON
                        ) {
                            targetPosX = cornerPos[0];
                            targetPosY = cornerPos[1];
                        }
                    }

                    if (soldier.getAttackType() == BattleConst.MELEE_ATTACK_TYPE) {
                        pathToTarget = mapGraph.findPathByCoords(soldier.getBattleX(), soldier.getBattleY(), targetPosX, targetPosY);
                    } else {
                        // find all points can reach and find path
                        int[][] candTargetPoss = Common.getNeighborPoints(targetPosX, targetPosY, soldier.getAttackRange());
                        Arrays.sort(candTargetPoss, (int[] pos1, int[] pos2) -> {
                            double diff = Common.calcSquareDistance(pos1[0], pos1[1], soldier.getBattleX(), soldier.getBattleY()) - Common.calcSquareDistance(pos2[0], pos2[1], soldier.getBattleX(), soldier.getBattleY());
                            return Double.compare(diff, 0);
                        });
                        for (int[] candTargetPos : candTargetPoss) {
                            pathToTarget = mapGraph.findPathByCoords(soldier.getBattleX(), soldier.getBattleY(), candTargetPos[0], candTargetPos[1]);
                            if (pathToTarget != null) {
                                targetPosX = candTargetPos[0];
                                targetPosY = candTargetPos[1];
                                break;
                            }
                        }
                    }
                    if(pathToTarget == null) {
                        // handle breaking wall to have path
                        target = findWallToBreak(soldier, target);
                        if(target == null) {
                            continue;
                        }
                        soldier.setTarget(target);
                        int vertexIndex = 0;
                        // vertexIndex follow left to right, top to bottom
                        while(pathToTarget == null && vertexIndex < 5) {
                            vertexIndex += 1;
                            if(vertexIndex == 1 || vertexIndex == 3) {
                                targetPosX = target.getX();
                            }
                            else {
                                targetPosX = target.getX() + 1;
                            }
                            if(vertexIndex < 3) {
                                targetPosY = target.getY();
                            }
                            else {
                                targetPosY = target.getY() + 1;
                            }
                            pathToTarget = mapGraph.findPathByCoords(soldier.getBattleX(), soldier.getBattleY(), targetPosX, targetPosY);
                        }
                        if(pathToTarget == null) {
                            break;
                        }
                        if(soldier.getAttackType() == BattleConst.RANGED_ATTACK_TYPE) {
                            boolean isPrune = false;
                            for(int i = pathToTarget.size() - 2; i > 0; i--) {
                                int[] coord = pathToTarget.get(i);
                                if(Common.calcSquareDistance(coord[0], coord[1], soldier.getX(), soldier.getY())
                                        < Math.pow(soldier.getAttackRange(), 2)) {
                                    isPrune = true;
                                }
                                else {
                                    if(isPrune) {
                                        pathToTarget = new ArrayList<>(pathToTarget.subList(0, i + 1));
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    soldierTargetPaths.add(new SoldierTargetPath(
                            soldier.getType(),
                            (int) Math.round(soldier.getX()),
                            (int) Math.round(soldier.getY()),
                            new ArrayList<>(pathToTarget),
                            target,
                            targetPosX,
                            targetPosY
                    ));
                }
                // add random location around target
                final int SPACING_NUMBER = 10;
                if(soldier.getAttackType() == BattleConst.MELEE_ATTACK_TYPE) {
                    boolean isRandomOnHorizontal = false;
                    if(target instanceof Wall) {
                        if(targetPosX > target.getX()) {
                            if(mapGraph.hasPathCoord(targetPosX - 1, targetPosY, targetPosX, targetPosY)) {
                                isRandomOnHorizontal = true;
                            }
                        } else {
                            if(mapGraph.hasPathCoord(targetPosX + 1, targetPosY, targetPosX, targetPosY)) {
                                isRandomOnHorizontal = true;
                            }
                        }
                    } else {
                        isRandomOnHorizontal = lcg.getNext() % 2 != 0;
                    }
                    if(isRandomOnHorizontal) {
                        targetPosX = target.getX() + ((double)target.getWidth()) / SPACING_NUMBER * (lcg.getNext() % SPACING_NUMBER);
                    } else {
                        targetPosY = target.getY() + ((double)target.getHeight()) / SPACING_NUMBER * (lcg.getNext() % SPACING_NUMBER);
                    }
                }
                else {
                    // set random location for ranged soldier
                    double randRangedX = 0;
                    double randRangedY = 0;
                    // soldier on top left of target
                    if(targetPosX < target.getX() && targetPosY < target.getY()) {
                        if(mapGraph.hasPathCoord(targetPosX, targetPosY, targetPosX + 1, targetPosY + 1)) {
                            randRangedX = 1;
                            randRangedY = 1;
                        } else if (mapGraph.hasPathCoord(targetPosX, targetPosY, targetPosX + 1, targetPosY)) {
                            randRangedX = 1;
                        } else if (mapGraph.hasPathCoord(targetPosX, targetPosY, targetPosX, targetPosY + 1)) {
                            randRangedY = 1;
                        }
                    }
                    // soldier on top right of target
                    if (targetPosX > target.getX() && targetPosY < target.getY()) {
                        if (this.mapGraph.hasPathCoord(targetPosX, targetPosY, targetPosX - 1, targetPosY + 1)) {
                            randRangedX = -1;
                            randRangedY = 1;
                        } else if (this.mapGraph.hasPathCoord(targetPosX, targetPosY, targetPosX - 1, targetPosY)) {
                            randRangedX = -1;
                        } else if (this.mapGraph.hasPathCoord(targetPosX, targetPosY, targetPosX, targetPosY + 1)) {
                            randRangedY = 1;
                        }
                    }
                    // soldier on bottom left of target
                    if (targetPosX < target.getX() && targetPosY > target.getY()) {
                        if (this.mapGraph.hasPathCoord(targetPosX, targetPosY, targetPosX + 1, targetPosY - 1)) {
                            randRangedX = 1;
                            randRangedY = -1;
                        } else if (this.mapGraph.hasPathCoord(targetPosX, targetPosY, targetPosX + 1, targetPosY)) {
                            randRangedX = 1;
                        } else if (this.mapGraph.hasPathCoord(targetPosX, targetPosY, targetPosX, targetPosY - 1)) {
                            randRangedY = -1;
                        }
                    }
                    // soldier on bottom right of target
                    if (targetPosX > target.getX() && targetPosY > target.getY()) {
                        if (this.mapGraph.hasPathCoord(targetPosX, targetPosY, targetPosX - 1, targetPosY - 1)) {
                            randRangedX = -1;
                            randRangedY = -1;
                        } else if (this.mapGraph.hasPathCoord(targetPosX, targetPosY, targetPosX - 1, targetPosY)) {
                            randRangedX = -1;
                        } else if (this.mapGraph.hasPathCoord(targetPosX, targetPosY, targetPosX, targetPosY - 1)) {
                            randRangedY = -1;
                        }
                    }
                    targetPosX = targetPosX + randRangedX / SPACING_NUMBER * (this.lcg.getNext() % SPACING_NUMBER);
                    targetPosY = targetPosY + randRangedY / SPACING_NUMBER * (this.lcg.getNext() % SPACING_NUMBER);
                }
                ArrayList<double[]> pathToTargetFinal = new ArrayList<>();
                for(int[] coord: pathToTarget) {
                    pathToTargetFinal.add(Arrays.stream(coord).asDoubleStream().toArray());
                }
                pathToTargetFinal.add(new double[] {targetPosX, targetPosY});
                soldier.setPath(pathToTargetFinal);
            }
        }
        for(Soldier soldier: aliveSoldiers) {
            if(soldier.getStatus() == BattleConst.ATTACKING_SOLDIER_STATUS) {
                soldier.attackTarget();
            }
            if(soldier.getStatus() == BattleConst.MOVING_SOLDIER_STATUS) {
                soldier.move();
            }
        }

    }

    public void forwardStep() {
        timestep += 1;
        // add new soldier
        BattleSession.DropSoldier dropSoldier = dropSoldiers.peek();
        if(dropSoldier != null && dropSoldier.getTimestep() == timestep) {
            dropSoldiers.poll();
            addSoldier(dropSoldier.getSoldierType(), dropSoldier.getX(), dropSoldier.getY());
        }
        simulateSoldiers();
        updateBuildings();
        // TODO: code simulate defense buildings attack soldier
        updateSoldiers();

        if(debug) {
            for(Soldier soldier: aliveSoldiers) {
                String pathStr = "[" + soldier.getPath().stream().map(Arrays::toString).collect(Collectors.joining(", ")) + "]";
                stateLogger.append(String.format("S - id: %d - timestep: %d - status: %d - x: %.2f - y: %.2f - health: %d - targetId: %d - targetPosX: %.2f - targetPosY: %.2f - path: %s\n",
                        soldier.getId(), timestep, soldier.getStatus(), soldier.getX(),
                        soldier.getY(), (int) soldier.getHealth(), (soldier.getTarget() == null ? -1 :soldier.getTarget().getId()), soldier.getTargetPosX(), soldier.getTargetPosY(), pathStr));
            }
            for(Building building: aliveBuildings) {
                stateLogger.append(String.format("B - id: %d - timestep: %d - health: %d\n", building.getId(), timestep, (int) building.getHealth()));
            }
        }
    }

    public void updateBuildings() {
        aliveBuildings.removeIf(building -> {
            boolean isDead = !building.isAlive();
            if(isDead) {
                mapGraph.addEmptyArea(building.getX(), building.getY(), building.getWidth(), building.getHeight());
            }
            return isDead;
        });
        aliveWallBuildings.removeIf(building -> {
            boolean isDead = !building.isAlive();
            if(isDead) {
                mapGraph.addEmptyArea(building.getX(), building.getY(), building.getWidth(), building.getHeight());
            }
            return isDead;
        });
        breakingWalls.removeIf(breakingWall -> !breakingWall.wall.isAlive() || !breakingWall.buildingTarget.isAlive());
        soldierTargetPaths.removeIf(soldierTargetPath -> !soldierTargetPath.target.isAlive());
    }

    public void updateSoldiers() {
        // TODO: wait for merge vietnha code
    }

    public String getStateLogger() {
        return stateLogger.toString();
    }
}

