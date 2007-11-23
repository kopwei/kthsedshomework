/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ksu.cis.wumpus;

import java.awt.Point;
import java.util.Vector;


/**
 * This class is used to 
 * @author Kop
 */
public class AgentCoordinate {
    private int xLoc = 1;
    private int yLoc = 1;
    private Vector<Point> unexploredDirections = new Vector<Point>();
    
    /**
     * Constructor for
     * @param x
     * @param y
     * @param unexploredDirections
     */
    public AgentCoordinate(int x, int y, Vector<Point> unexploredDirections) {
        this.xLoc = x;
        this.yLoc = y;
        this.unexploredDirections = unexploredDirections;
    }
    
    public Point getLocation() {
        return new Point(xLoc, yLoc);
    }
    
    public int getX() { return xLoc; }
    public int getY() { return yLoc; }
    
    /**
     * Get the numbers of unexplorered directions
     * @return
     */
    public int getMultiplicity() { return unexploredDirections.size(); }
    
    /**
     * This method is used to remove an unexplored direction (e.g. a direction is revealed)
     * @param direction the direction
     */
    public void removeUnexploredDirection(Point direction) {
        unexploredDirections.removeElement(direction);
    }
    /**
     * This method is used to get all the un-explored directions
     * @return the vector of directions
     */
    public Vector<Point> getAllUnexploredDirections() {
        return unexploredDirections;
    }
}
