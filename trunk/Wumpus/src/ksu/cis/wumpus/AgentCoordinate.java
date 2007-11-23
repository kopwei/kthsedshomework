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
    private boolean isMultiple = false;
    
    public AgentCoordinate(int x, int y, boolean isMultiple) {
        this.xLoc = x;
        this.yLoc = y;
        this.isMultiple = isMultiple;
    }
    
    public int getX() { return xLoc; }
    public int getY() { return yLoc; }
    public boolean isMultiple() { return isMultiple; }
    public void setMultiple(boolean isMultiple) { this.isMultiple = isMultiple; }
}
