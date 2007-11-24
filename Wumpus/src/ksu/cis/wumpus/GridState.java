/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ksu.cis.wumpus;

/**
 *
 * @author Kop
 */
public class GridState {
    private int pitSuspiciousLevel = 0;
    private int wumpusSuspiciousLevel = 0;
    private boolean isDeadWumpus = false;
    private boolean isGold = false;
    private boolean isWall = false;
    private boolean unvisited = true;
    
    public boolean isSuspiciousPit() {
        return (0 < pitSuspiciousLevel && pitSuspiciousLevel < 4);
    }

    public boolean isSuspiciousWumpus() {
        return (0 < wumpusSuspiciousLevel && wumpusSuspiciousLevel < 2);
    }
    
    public boolean isWall() {
        return isWall;
    }
    
    public boolean isGold() {
        return isGold;
    }
    
    public boolean isWumpus() {
        return (wumpusSuspiciousLevel == 2);
    }
    
    public boolean isPit() {
        return (pitSuspiciousLevel == 4);
    }
    
    public boolean isSafe() {
        if (pitSuspiciousLevel > 0 || wumpusSuspiciousLevel > 0) {
            return false;
        }
        else {
            return true;
        }
    }
    
    public boolean isDeadWumpus() {
        return isDeadWumpus;
    }
    
    public boolean isUnvisited() {
        return unvisited;
    }
    
    public int getDangerLevel() {
        int danger = 0;
        if (pitSuspiciousLevel > 0) danger += pitSuspiciousLevel;
        if (wumpusSuspiciousLevel > 0) danger += wumpusSuspiciousLevel * 2;
        return danger;
    }
    
    
            
    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////
           
    public void setSafe() {
        pitSuspiciousLevel = -1;
        wumpusSuspiciousLevel = -1;
    }
    
    public void setDefinetlyNotPit() {
        pitSuspiciousLevel = -1;
        if (wumpusSuspiciousLevel < 1) {
            unvisited = true;
        }
    }
    
    public void setDefinetlyNotWumpus() {
        if (pitSuspiciousLevel < 1 && wumpusSuspiciousLevel > 0) {
            unvisited = true;
        }
        wumpusSuspiciousLevel = -1;
    }
    
    /**
     * 
     */
    public void setSuspiciousPit() {
        unvisited = false;
        if (4 != pitSuspiciousLevel) {
            pitSuspiciousLevel++;
            if (4 == pitSuspiciousLevel) {
                wumpusSuspiciousLevel = -1;
            }
        }
    }
    
    /**
     * 
     */
    public void setSuspiciousWumpus() {
        unvisited = false;
        if (2 != wumpusSuspiciousLevel && -1 != wumpusSuspiciousLevel) {
            wumpusSuspiciousLevel++;
            if (2 == wumpusSuspiciousLevel) {
                pitSuspiciousLevel = -1;
            }
        }
    }
    
    public void setWumpus() {
        unvisited = false;
        wumpusSuspiciousLevel = 2;
        pitSuspiciousLevel = -1;
    }
    
    public void setPit() {
        unvisited = false;
        pitSuspiciousLevel = 4;
        wumpusSuspiciousLevel = -1;
    }
    
    public void setWall() {
        unvisited = false;
        isWall = true;
        pitSuspiciousLevel = -1;
        wumpusSuspiciousLevel = -1;
    }
    
    public void setGold() {
        unvisited = false;
        isGold = true;
        pitSuspiciousLevel = -1;
        wumpusSuspiciousLevel = -1;
    }
    
    public void setDeadWumpus() {
        unvisited = false;
        isDeadWumpus = true;
        pitSuspiciousLevel = -1;
        wumpusSuspiciousLevel = -1;
    }
    
    public void setVisited() {
        unvisited = false;
    }
    
    public void setUnVisited() {
        unvisited = true;
    }
    
}
