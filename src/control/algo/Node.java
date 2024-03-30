package control.algo;

import java.awt.*;

/**
 * Node classe pour A* algorithm
 */
public class Node {
    Point position;
    Node parent;
    int g;
    double h, f;


    public Node(Point position, Node parent, int g, double h) {
        this.position = position;
        this.parent = parent;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }
}
