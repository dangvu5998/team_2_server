package model.battle;

import model.map.MapObject;
import model.map.Wall;
import util.Common;

import java.util.*;

public class MapBattleGraph {
    private int nbOfVertices;
    private int width;
    private int height;
    private int startX;
    private int startY;
    private HashMap<Integer, ArrayList<Integer>> path;

    public MapBattleGraph(int width, int height, int startX, int startY) {
        nbOfVertices = width * height;
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
        path = new HashMap<>();
        for (int v = 0; v < nbOfVertices; v++) {
            path.put(v, new ArrayList<>());
        }
    }

    private void addPath(int v1, int v2) {
        if (!path.get(v1).contains(v2)) {
            path.get(v1).add(v2);
            path.get(v2).add(v1);
            Collections.sort(path.get(v1));
            Collections.sort(path.get(v2));
        }
    }

    /**
     * Convert coordinates to vertext in graph
     *
     * @param x x
     * @param y y
     * @return vertex id
     */
    private int coordsToVertex(int x, int y) {
        return (y - startY) * width + (x - startX);
    }

    public boolean hasPathCoord(int x1, int y1, int x2, int y2) {
        int v1 = coordsToVertex(x1, y1);
        int v2 = coordsToVertex(x2, y2);
        return path.get(v1).contains(v2);
    }

    public boolean hasPathCoord(double x1, double y1, double x2, double y2) {
        return hasPathCoord((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2));
    }

    private int[] vertexToCoords(int v) {
        int x = v % width;
        int y = (v - x) / height;
        x += startX;
        y += startY;
        return new int[]{x, y};
    }

    static class VertexDistance implements Comparable<VertexDistance> {
        private final int id;
        private final int[] coord;
        private final double currDistance;
        private final double potentialDistance;

        public VertexDistance(int id, int[] coord, double currDistance, double potentialDistance) {
            this.id = id;
            this.coord = coord;
            this.currDistance = currDistance;
            this.potentialDistance = potentialDistance;
        }

        public int getId() {
            return id;
        }

        public int[] getCoord() {
            return coord;
        }

        public double getCurrDistance() {
            return currDistance;
        }

        public double getPotentialDistance() {
            return potentialDistance;
        }

        @Override
        public int compareTo(VertexDistance o) {
            if(potentialDistance < o.potentialDistance) {
                return -1;
            }
            if(potentialDistance > o.potentialDistance) {
                return 1;
            }
            if(currDistance > o.currDistance) {
                return -1;
            }
            if(currDistance < o.currDistance) {
                return 1;
            }
            return Integer.compare(id, o.id);
        }
    }
    /**
     * Find path between 2 vertices
     * @param srcV source vertex
     * @param dstV destination vertex
     * @param upperBoundBonus bonus distance to manhattan allowed to early stop. Default is 14
     * @return list of vertices from src to dst. If there is no path, return null
     *
     */
    private ArrayList<Integer> findPathByVertices(int srcV, int dstV, int upperBoundBonus) {
        if(path.get(srcV).size() == 0 || path.get(dstV).size() == 0) {
            return null;
        }
        int[] srcCoord = vertexToCoords(srcV);
        int srcX = srcCoord[0];
        int srcY = srcCoord[1];
        int[] dstCoord = vertexToCoords(dstV);
        int dstX = dstCoord[0];
        int dstY = dstCoord[1];

        final int UPPER_BOUND_DISTANCE = Common.calcManhattanDistance(srcX, srcY, dstX, dstY) + upperBoundBonus;
        final double SQUARE_DISTANCE_UNIT = 1;
        final double DIAGONAL_DISTANCE_UNIT = 1.4;

        PriorityQueue<VertexDistance> verticesQueue = new PriorityQueue<>();
        verticesQueue.add(new VertexDistance(srcV,
                new int[]{srcX, srcY}, 0,
                Common.calcGridDistance(srcX, srcY, dstX, dstY)));
        HashMap<Integer, Integer> preVertexId = new HashMap<>();
        final int PRE_ROOT = -1;
        preVertexId.put(srcV, PRE_ROOT);
        HashSet<Integer> visitedVertexId = new HashSet<>();
        visitedVertexId.add(srcV);
        boolean pathFound = false;
        while (!verticesQueue.isEmpty()) {
            if(pathFound) {
                break;
            }
            VertexDistance vertexDistance = verticesQueue.poll();
            for(int verId: path.get(vertexDistance.getId())) {
                if(!visitedVertexId.contains(verId)) {
                    visitedVertexId.add(verId);
                    preVertexId.put(verId, vertexDistance.getId());
                    if(verId == dstV) {
                        pathFound = true;
                        break;
                    }
                    int[] currCoord = vertexToCoords(verId);
                    double currDistance = vertexDistance.getCurrDistance();
                    if(currCoord[0] == vertexDistance.getCoord()[0] || currCoord[1] == vertexDistance.getCoord()[1]) {
                        currDistance += SQUARE_DISTANCE_UNIT;
                    }
                    else {
                        currDistance += DIAGONAL_DISTANCE_UNIT;
                    }
                    double potentialDistance = currDistance + Common.calcGridDistance(currCoord[0], currCoord[1], dstX, dstY);
                    if(potentialDistance > UPPER_BOUND_DISTANCE) {
                        continue;
                    }
                    verticesQueue.add(new VertexDistance(verId, currCoord, currDistance, potentialDistance));
                }
            }
        }

        if(pathFound) {
            // trace path
            ArrayList<Integer> pathNodes = new ArrayList<>();
            pathNodes.add(dstV);
            int currV = dstV;
            while (preVertexId.get(currV) != PRE_ROOT) {
                currV = preVertexId.get(currV);
                pathNodes.add(currV);
            }
            Collections.reverse(pathNodes);
            return pathNodes;
        }
        return null;
    }

    public ArrayList<int[]> findPathByCoords(int srcX, int srcY, int dstX, int dstY) {
        int srcV = coordsToVertex(srcX, srcY);
        int dstV = coordsToVertex(dstX, dstY);
        ArrayList<Integer> pathV = findPathByVertices(srcV, dstV);
        if(pathV == null) {
            return null;
        }
        ArrayList<int[]> result = new ArrayList<>();
        for(int verId: pathV) {
            result.add(vertexToCoords(verId));
        }
        return result;
    }

    public ArrayList<int[]> findPathByCoords(double srcX, double srcY, double dstX, double dstY) {
        int roundedSrcX = (int) Math.round(srcX);
        int roundedSrcY = (int) Math.round(srcY);
        int roundedDstX = (int) Math.round(dstX);
        int roundedDstY = (int) Math.round(dstY);
        return findPathByCoords(roundedSrcX, roundedSrcY, roundedDstX, roundedDstY);
    }

    private ArrayList<Integer> findPathByVertices(int srcV, int dstV) {
        return findPathByVertices(srcV, dstV, 16);
    }

    public void loadGraphFromMapObjs(ArrayList<MapObject> mapObjs) {
        // grid build from map objects, -1 mean no map object, otherwise it's id of map obj
        // wall has value 1900
        // grid have top left cell corresponds to vertex 0
        final int EMPTY_CELL = -1;
        final int WALL_CELL = 1900;
        // subtract border for soldier, we get real size map for map objects
        int gridWidth = width - 3;
        int gridHeight = height - 3;
        int[][] grid = new int[gridHeight][gridWidth];
        for(int y = 0; y < gridHeight; y++) {
            Arrays.fill(grid[y], EMPTY_CELL);
        }
        for(MapObject mapObj: mapObjs) {
            int cellX = mapObj.getX();
            int cellWidth = mapObj.getWidth();
            int cellY = mapObj.getY();
            int cellHeight = mapObj.getHeight();
            for(int x = cellX; x < cellX + cellWidth; x++) {
                for(int y = cellY; y < cellY + cellHeight; y++) {
                    if(mapObj instanceof Wall) {
                        grid[y][x] = WALL_CELL;
                    }
                    else {
                        grid[y][x] = mapObj.getId();
                    }
                }
            }
        }
        // init path for border
        for(int x = startX + 1; x < width + startX - 1; x++) {
            // init top border
            int currV = coordsToVertex(x, startY);
            int leftBottomV = coordsToVertex(x - 1, startY + 1);
            int rightV = coordsToVertex(x + 1, startY);
            int rightBottomV = coordsToVertex(x + 1, startY + 1);
            int bottomV = coordsToVertex(x, startY + 1);
            addPath(currV, leftBottomV);
            addPath(currV, rightV);
            addPath(currV, rightBottomV);
            addPath(currV, bottomV);
            // init bottom border
            currV = coordsToVertex(x, height + startY - 1);
            int topLeftV = coordsToVertex(x, height + startY - 2);
            int topV = coordsToVertex(x, height + startY - 2);
            int topRightV = coordsToVertex(x + 1, height + startY - 2);
            rightV = coordsToVertex(x + 1, height + startY - 1);
            addPath(currV, topLeftV);
            addPath(currV, topV);
            addPath(currV, topRightV);
            addPath(currV, rightV);
        }
        for(int y = startY + 1; y < height + startY - 1; y++) {
            // init left border
            int currV = coordsToVertex(startX, y);
            int topV = coordsToVertex(startX, y - 1);
            int topRightV = coordsToVertex(startX + 1, y - 1);
            int rightV = coordsToVertex(startX + 1, y);
            int bottomRightV = coordsToVertex(startX + 1, y + 1);
            addPath(currV, topV);
            addPath(currV, topRightV);
            addPath(currV, rightV);
            addPath(currV, bottomRightV);
            // init right border
            currV = coordsToVertex(width + startX - 1, y);
            topV = coordsToVertex(width + startX - 1, y - 1);
            int topLeftV = coordsToVertex(width + startX - 2, y - 1);
            int leftV = coordsToVertex(width + startX - 2, y);
            int bottomLeftV = coordsToVertex(width + startX - 2, y + 1);
            addPath(currV, topV);
            addPath(currV, topLeftV);
            addPath(currV, leftV);
            addPath(currV, bottomLeftV);
        }
        // handle top left corner
        int maxCoordX = width + startX - 1;
        int maxCoordY = height + startY - 1;
        int cornerV = coordsToVertex(startX, startY);
        addPath(cornerV, coordsToVertex(startX + 1, startY));
        addPath(cornerV, coordsToVertex(startX, startY + 1));
        addPath(cornerV, coordsToVertex(startX + 1, startY + 1));
        // handle top right
        cornerV = coordsToVertex(maxCoordX, startY);
        addPath(cornerV, coordsToVertex(maxCoordX - 1, startY));
        addPath(cornerV, coordsToVertex(maxCoordX, startY + 1));
        addPath(cornerV, coordsToVertex(maxCoordX - 1, startY + 1));
        // handle bottom left
        cornerV = coordsToVertex(startX, maxCoordY);
        addPath(cornerV, coordsToVertex(startX, maxCoordY - 1));
        addPath(cornerV, coordsToVertex(startX + 1, maxCoordY));
        addPath(cornerV, coordsToVertex(startX + 1, maxCoordY - 1));
        // handle bottom right
        cornerV = coordsToVertex(maxCoordX, maxCoordY);
        addPath(cornerV, coordsToVertex(maxCoordX - 1, maxCoordY));
        addPath(cornerV, coordsToVertex(maxCoordX, maxCoordY - 1));
        addPath(cornerV, coordsToVertex(maxCoordX - 1, maxCoordY - 1));

        // init path inside map
        for(int y = 0; y < gridHeight; y++) {
            for(int x = 0; x < gridWidth; x++) {
                int topLeftVertexId = coordsToVertex(x, y);
                int topRightVertexId = coordsToVertex(x + 1, y);
                int bottomLeftVertexId = coordsToVertex(x, y + 1);
                int bottomRightVertexId = coordsToVertex(x + 1, y + 1);
                if(grid[y][x] == EMPTY_CELL) {
                    // add 2 path diagonal
                    addPath(topLeftVertexId, bottomRightVertexId);
                    addPath(topRightVertexId, bottomLeftVertexId);
                    // add 2 path top and left
                    addPath(topLeftVertexId, topRightVertexId);
                    addPath(topLeftVertexId, bottomLeftVertexId);
                }
                else {
                    // add 2 path top and left
                    if(y == 0 || grid[y - 1][x] != grid[y][x]) {
                        addPath(topLeftVertexId, topRightVertexId);
                    }
                    if(x == 0 || grid[y][x - 1] != grid[y][x]) {
                        addPath(topLeftVertexId, bottomLeftVertexId);
                    }
                }
                // check is border and add path in bottom and right
                if(y == height - 1) {
                    addPath(bottomLeftVertexId, bottomRightVertexId);
                }
                if(x == width - 1) {
                    addPath(topRightVertexId, bottomRightVertexId);
                }
            }
        }
    }

    public void addEmptyArea(int x, int y, int width, int height) {
        for(int i = x; i < x + width; i++) {
            for(int j = y; j < y + height; j++) {
                int topLeftV = coordsToVertex(i, j);
                int topRightV = coordsToVertex(i + 1, j);
                int bottomLeftV = coordsToVertex(i ,j + 1);
                int bottomRightV = coordsToVertex(i + 1, j + 1);
                addPath(topLeftV, topRightV);
                addPath(topLeftV, bottomLeftV);
                addPath(topLeftV, bottomRightV);
                addPath(topRightV, bottomLeftV);
                if(i == x + width - 1) {
                    addPath(topRightV, topRightV);
                }
                if(j == y + height - 1) {
                    addPath(bottomLeftV, bottomRightV);
                }
            }
        }
    }
}
