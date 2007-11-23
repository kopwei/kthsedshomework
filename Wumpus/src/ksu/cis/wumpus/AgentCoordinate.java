/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ksu.cis.wumpus;


/**
 *
 * @author Kop
 */
public class AgentCoordinate {
    private int xLoc = 1;
    private int yLoc = 1;
    private int multiplicity = 0;
    
    public AgentCoordinate(int x, int y, int multiplicity) {
        this.xLoc = x;
        this.yLoc = y;
        this.multiplicity = multiplicity;
    }
    
    public int getX() { return xLoc; }
    public int getY() { return yLoc; }
    public int getMultiplicity() { return multiplicity; }
    public void setMultiplicity(int multiplicity) { this.multiplicity = multiplicity; }
}
