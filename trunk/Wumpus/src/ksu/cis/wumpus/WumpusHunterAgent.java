package ksu.cis.wumpus;

/* fill in the methods of this class ... you may add or delete any methods
   algorithms, or data structures you need. */

// Unvisited = 0
// Safe = 1
// Breeze = 2
// Smell = 3
// Wump

import java.awt.Point;
import java.io.*;
import java.util.Stack;

enum GridState {

}

public class WumpusHunterAgent implements AgentProgram {
    static final int UNVISITED = 0;
    static final int SAFE = 1;
    static final int BREEZE = 2;
    static final int SMELL = 3;
    static final int WUMPUS = 4;
    static final int PIT = 5;
    static final int WALL = 6;
    // constants

    private final int right = 0;
    private final int left = 1;
    private final int up = 2;
    private final int down = 3;

    // hidden attributes.  xLoc and yLoc are for the agent to "know" where it is

    private int xSize,  ySize,  xLoc,  yLoc;
    private Point heading = new Point();
    private int gridMemory[][][];
    private Stack<AgentCoordinate> agentTrace = new Stack<AgentCoordinate>();
    private Point lastHeading = new Point();

    public WumpusHunterAgent(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        xLoc = 1;
        yLoc = 1;
        gridMemory = new int[xSize][ySize][10];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; i < ySize; j++) {
                if (i == 0 || i== xSize - 1 || j == 0 || j == ySize - 1) {
                    gridMemory[i][j][WALL] = 1; // set the grid as wall  
                }
                else {
                    gridMemory[i][j][UNVISITED] = 1; // Set the grid as non-visited
                }
            }
        }
        
    }
    //Main Method

    public Action execute(Percept perceptArg, Agent agent) {
        //((AgentThing)agent)
        AgentThing agentThing = (AgentThing)agent;
        if (null == perceptArg || null == agentThing) return null;
        xLoc = agentThing.location.x;
        yLoc = agentThing.location.y;
        heading = agentThing.heading;
        
        WumpusPercept percept = (WumpusPercept) perceptArg;
        memorizeState(percept, agentThing);

        if (percept.isGlitter) {
            return new AnAction("grab");
        }

        if (percept.isBump) {
            return new AnAction("turn", AIMA.randomChoice("right", "left"));
        }

        if (percept.isBreeze) {
            if (AIMA.random() < .60) {
                return new AnAction("turn", AIMA.randomChoice("right", "left"));
            }
        }

        if (percept.isStench) {
            if (AIMA.random() < .08) {
                return new AnAction("shoot");
            } else if (AIMA.random() < .3) {
                return new AnAction("forward");
            } else {
                return new AnAction("turn", AIMA.randomChoice("right", "left"));
            }
        }

        if (AIMA.random() < .8) {
            return new AnAction("forward");
        } else {
            return new AnAction("turn", AIMA.randomChoice("right", "left"));
        }

    }
    
    private void memorizeState(WumpusPercept percept, AgentThing agent) {
        if (null == percept || null == agent) return;
        if (percept.isGlitter) {
            gridMemory[xLoc][yLoc][SAFE] = 1;
            
            if (agentTrace.empty()) {
                lastHeading.x = heading.x;
                lastHeading.y = heading.y;
            }
            else {
                lastHeading.x = xLoc - agentTrace.peek().getX();
                lastHeading.y = yLoc - agentTrace.peek().getY();
            }
            
            if (lastHeading.equals(new Point(1,0))) { //
            
            } else {
                if (lastHeading.equals(new Point(0,1))) {
                    //
                }
                else {
                    if (lastHeading.equals(new Point(-1,0))) {
                        //
                    }
                    else { // lastHeading = (0,-1)
                        //
                    }
                }
            }
            
            // check surrounding fields
            // check if there are some fields that robot does not reach
            for (int i = 1; i < xSize - 1; i++) {
                for (int j = 1; j < ySize - 1; j++) {
                    if (gridMemory[i][j][UNVISITED] == 1) {
                        // check if one of surrounding fields of current position is unvisited, and break
                    }
                }


            }

            //agentTrace.push(new AgentCoordinate(up, up, isMultiple))
        }
    }
    
    //private
}
