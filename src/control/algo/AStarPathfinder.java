package control.algo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.concurrent.CompletableFuture;

/**
 * Fournit une implémentation de l'algorithme A* pour trouver le chemin le plus court entre deux points sur une grille.
 * La grille est définie par une dimension (largeur et hauteur) et un tableau indiquant les cases franchissables.
 */
public class AStarPathfinder {
    private final int width, height; //largeur et hauteur de la grille.
    private final boolean[][] walkable; //Une grille à deux dimensions indiquant si une case est franchissable.

    public AStarPathfinder(int width, int height, boolean[][] walkable) {
        this.width = width;
        this.height = height;
        this.walkable = walkable;
    }

    /**
     * Trouve le chemin le plus court entre le point de départ et le point d'arrivée.
     *
     * @param start Le point de départ.
     * @param end Le point d'arrivée.
     * @return Une liste de points représentant le chemin le plus court.
     */
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

    /**
     * Version asynchrone de findPath pour exécuter la recherche de chemin dans un thread séparé.
     *
     * @param start Le point de départ.
     * @param end Le point d'arrivée.
     * @return Un CompletableFuture contenant la liste des points du chemin trouvé.
     */
    public CompletableFuture<ArrayList<Point>> findPathAsync(Point start, Point end) {
        return CompletableFuture.supplyAsync(() -> findPath(start, end));
    }

    /**
     * Reconstruit le chemin trouvé à partir du dernier nœud.
     *
     * @param current Le dernier nœud du chemin.
     * @return Une liste de points représentant le chemin reconstruit.
     */
    private ArrayList<Point> reconstructPath(Node current) {
        ArrayList<Point> path = new ArrayList<>();
        while(current != null) {
            path.add(0,current.position);
            current = current.parent;
        }
        return path;
    }

    /**
     * Calcule les voisins franchissables d'un point donné sur la grille.
     *
     * @param p Le point pour lequel trouver les voisins.
     * @return Une liste des points voisins franchissables.
     */
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
