package control.algo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.concurrent.CompletableFuture;

public class AStarPathfinder {
    private final int width, height;
    private final boolean[][] walkable;

    public AStarPathfinder(int width, int height, boolean[][] walkable) {
        this.width = width;
        this.height = height;
        this.walkable = walkable;
    }

    public ArrayList<Point> findPath(Point start, Point end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.f));
        HashMap<Point, Node> allnodes = new HashMap<>();

        Point helper = new Point(start.x / GridSystem.CELL_SIZE, start.y / GridSystem.CELL_SIZE);

        Node startNode = new Node(helper, null, 0, start.distance(end));
        openSet.add(startNode);
        allnodes.put(helper, startNode);

        helper = new Point(end.x / GridSystem.CELL_SIZE, end.y / GridSystem.CELL_SIZE);

        while(!openSet.isEmpty()) {
            Node current = openSet.poll();

            if(current.position.equals(helper)) {
                return reconstructPath(current);
            }

            for(Point neighbor : this.getNeighbors(current.position)) {
                int tentativeG = current.g + 1;

                if(!allnodes.containsKey(neighbor) || tentativeG < allnodes.get(neighbor).g) {
                    Node neighborNode = new Node(neighbor, current, tentativeG, helper.distance(neighbor));
                    allnodes.putIfAbsent(neighbor, neighborNode);

                    neighborNode.g = tentativeG;
                    neighborNode.f = tentativeG + neighborNode.h;
                    neighborNode.parent = current;
                    if(!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    public CompletableFuture<ArrayList<Point>> findPathAsync(Point start, Point end) {
        return CompletableFuture.supplyAsync(() -> findPath(start, end));
    }

    private ArrayList<Point> reconstructPath(Node current) {
        ArrayList<Point> path = new ArrayList<>();
        while(current != null) {
            path.add(0,current.position);
            current = current.parent;
        }
        return path;
    }

    private ArrayList<Point> getNeighbors(Point p) {
        ArrayList<Point> neighbors = new ArrayList<>();
        for(int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                if(x == 0 && y == 0) continue;
                Point neighbor = new Point(p.x + x, p.y + y);
                if(neighbor.x >= 0 && neighbor.x < width && neighbor.y >= 0 && neighbor.y < height && walkable[neighbor.x][neighbor.y]) {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }

}
