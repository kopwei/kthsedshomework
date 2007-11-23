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
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;


public class WumpusHunterAgent implements AgentProgram {
//    static final int UNVISITED = 0;
//    static final int SAFE = 1;
//    static final int BREEZE = 2;
//    static final int SMELL = 3;
//    static final int WUMPUS = 4;
//    static final int PIT = 5;
//    static final int WALL = 6;
////    static final int BREEZEANDGOLd = 7;
////    static final int SMELLANDGOLD = 8;
//    static final int BREEZEANDSMELL = 9;
////    static final int BREEZESMELLANDGOLD = 10;
//    static final int WUMPUSANDPIT = 11;
//    static final int PIT2 = 12;
//    static final int PIT3 = 13;
//    static final int PIT4 = 14;
//    // constants
//

    private final int right = 0;
    private final int down = 1;
    private final int left = 2;
    private final int up = 3;
    private int currentDirection = 0;

    // hidden attributes.  xLoc and yLoc are for the agent to "know" where it is

    private int xSize,  ySize,  xLoc,  yLoc;
    private Point heading = new Point();
    private GridState gridMemory[][];
    private ArrayDeque<AgentCoordinate> agentTrace = new ArrayDeque<AgentCoordinate>();
    private Point lastHeading = new Point();
    private Point needHeading = new Point();
    private int multiplicity = 0;
    private ArrayDeque<Action> actionPool = new ArrayDeque<Action>();
    private Vector<Point> arroundPoints = new Vector<Point>();

    public WumpusHunterAgent(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        xLoc = 1;
        yLoc = 1;
        gridMemory = new GridState[ySize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; i < ySize; j++) {
                if (i == 0 || i== xSize - 1 || j == 0 || j == ySize - 1) {
                    gridMemory[i][j].setWall(); // set the grid as wall  
                }
            }
        }
        
    }
    //Main Method

    public Action execute(Percept perceptArg, Agent agent) {
        if (!actionPool.isEmpty()) {
            return actionPool.pollFirst();
        }
        
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
        gridMemory[xLoc][yLoc].setSafe();
        if (percept.isGlitter) 
        {
            gridMemory[xLoc][yLoc].setGold();
            return;
        }
        if (percept.isBump) {
            gridMemory[xLoc + agent.heading.x][yLoc + agent.heading.y].setWall();
            return;
        }
        if (percept.isBreeze) {  
            fillArroundPoints();
            for (Point point : arroundPoints) {
                gridMemory[point.x][point.y].setSuspiciousPit();
            }
        }
        if (percept.isScream) {
            for (int i = 1; i < xSize - 1; i++) {
                for (int j = 1; j < ySize - 1; j++) {
                    if (!gridMemory[i][j].isWall()) {   
                        gridMemory[i][j].setDefinetlyNotWumpus();
                    }
                }
            }
            gridMemory[xLoc][yLoc].setDeadWumpus();
        }
        if (percept.isStench) {
            fillArroundPoints();
            for (Point point : arroundPoints) {
                gridMemory[point.x][point.y].setSuspiciousWumpus();
            }
        }
    }
    
    private void fillArroundPoints() {
        arroundPoints.clear();
        Point lastPosition = agentTrace.peekLast().getLocation();
        Point[] arroundPoint = {
            new Point(xLoc + 1, yLoc), 
            new Point(xLoc, yLoc + 1), 
            new Point(xLoc - 1, yLoc),
            new Point(xLoc, yLoc - 1)
        };
        for (Point point : arroundPoint) {
            GridState gridState = gridMemory[point.x][point.y];
            if (!gridState.isWall() && !point.equals(lastPosition)) {
                arroundPoints.addElement(point);
            }
        }
    }
            ///////////////
//            gridMemory[xLoc][yLoc] = SAFE;
//            
//            if (agentTrace.empty()) {
//                lastHeading.x = heading.x;
//                lastHeading.y = heading.y;
//            }
//            else {
//                lastHeading.x = xLoc - agentTrace.peek().getX();
//                lastHeading.y = yLoc - agentTrace.peek().getY();
//            }
//            
//            checkSurroundingPoints();
//            
//            calMultiplicity();
//            if (multiplicity == 0) {
//                // check all fields if there are some ones that robot does not reach
//                for (int i = 1; i < xSize - 1; i++) {
//                    for (int j = 1; j < ySize - 1; j++) {
//                        if (gridMemory[i][j] == UNVISITED) {
//                            moveBack();
//                        }
//                        else {
//                            // decide where the wumpus is, and shoot
//                        }
//                    }
//                }
//            }
//            else {
//                // there are one or more surrounding fields unvisited
//                agentTrace.add(new AgentCoordinate(xLoc, yLoc, multiplicity - 1));
//                for (Iterator<Point> it = surroundingPoints.iterator(); it.hasNext();) {
//                    if (gridMemory[it.next().x][it.next().y] == UNVISITED) {
//                        needHeading.x = it.next().x - xLoc;
//                        needHeading.y = it.next().y - yLoc;
//                        break;
//                    }
//                }
//                // compare needHeading to current heading, decide how to turn
//                
//            }
//        }
//        
//        if (percept.isBump) {
//            gridMemory[xLoc + heading.x][yLoc + heading.y] = WALL;
//            // check left and right side of current heading
//            lastHeading.x = heading.x;
//            lastHeading.y = heading.y;
//            
//            checkSurroundingPoints();
//            // remove the foward point from the surrounding points
//            surroundingPoints.remove(new Point(xLoc + heading.x, yLoc + heading.y));
//        }
    
    
    
    private Action decideAction() {
        GridState gs = gridMemory[xLoc][yLoc];
        if (gs.isGold()) {
            setActionsToExit();
            return new AnAction("grab");
        }
        
        fillArroundPoints();
        Vector<Point> unvisitedPoints = new Vector<Point>();
        for (int i = 0; i < arroundPoints.size(); i++) {
            Point point = arroundPoints.elementAt(i);
            if (gridMemory[point.x][point.y].isUnvisited()) unvisitedPoints.add(point);
        }

        if (unvisitedPoints.size() > 0) {
            Point wantedHeading = new Point();
            Vector<Point> unexploredDirections = new Vector<Point>();
            for (int i = 0; i < unvisitedPoints.size(); i++) {
                Point point = unvisitedPoints.elementAt(i);
                unexploredDirections.add(new Point(point.x - xLoc, point.y - yLoc));
            }
            wantedHeading = unexploredDirections.firstElement();
            unexploredDirections.remove(0);
            agentTrace.add(new AgentCoordinate(xLoc, yLoc, unexploredDirections));
            setHowToTurn(wantedHeading);
            actionPool.add(new AnAction("forward"));
            AnAction actionForNow = (AnAction) actionPool.pollFirst();
            return actionForNow;
            // pop up the field of current position
//            AgentCoordinate ac = agentTrace.pollLast();
//            if (ac.getMultiplicity() == 0) {
//                extremeAction();
//                if (actionPool.isEmpty()) return new AnAction("climb");
//                else {
//                    AnAction actionForNow = (AnAction) actionPool.pollFirst();
//                    return actionForNow;
//                }
//            }
//            else {
//                Random rnd = new Random(System.currentTimeMillis());
//                int index = rnd.nextInt(ac.getMultiplicity());
//                Point wantedHeading = ac.getAllUnexploredDirections().get(index);
//                ac.removeUnexploredDirection(wantedHeading);
//                setHowToTurn(wantedHeading);
//                AnAction afn = (AnAction) actionPool.pollFirst();
//                return afn;
//            }
        }
        else {
            extremeAction();
            AnAction afn = (AnAction) actionPool.pollFirst();
            return afn;
        }
    }
    
    private void setActionsToExit() {
        Point lastPoint = new Point();
        Point backHeading = new Point();
        backHeading.x = heading.x;
        backHeading.y = heading.y;
        int backDirection = 0;

        // check which direction the heading is
        if (heading.x == 1 && heading.y == 0) {
            currentDirection = right;
        } else {
            if (heading.x == 0 && heading.y == 1) {
                currentDirection = down;
            } else {
                if (heading.x == -1 && heading.y == 0) {
                    currentDirection = left;
                } else {
                    currentDirection = up;
                }
            }
        }
        
        while (!agentTrace.isEmpty()) {
            AgentCoordinate ac = agentTrace.pollLast();
            lastPoint.x = ac.getX();
            lastPoint.y = ac.getY();
            backHeading.x = lastPoint.x - xLoc;
            backHeading.y = lastPoint.y - yLoc;
            
            backDirection = setHowToTurn(backHeading);
            actionPool.add(new AnAction("forward"));
            currentDirection = backDirection;
        }
        actionPool.add(new AnAction("climb"));
    }
    
    private void extremeAction() {
        int counter = 1;
        for (Iterator<AgentCoordinate> it = agentTrace.descendingIterator(); it.hasNext();) {
            AgentCoordinate agentCoordinate = it.next();
            if (agentCoordinate.getMultiplicity() == 0) counter++;
            else break;
        }
        if (counter - 1 == agentTrace.size()) {
            riskLife();
        }
        else moveBack(counter);
    }
    
    // find where the wumpus is and kill it when there are no un visited and safe fileds
    private void riskLife() {
        
    }
    
    // move back to the field whose multiplicity is not 0 when multiplicity of the current field is 0
    private void moveBack(int counter) {
        
    }
    
    private int setHowToTurn(Point wantedHeading) {
        // check which direction the backheading is
        int backDirection = 0;
        if (wantedHeading.x == 1 && wantedHeading.y == 0) {
            backDirection = right;
        } else {
            if (wantedHeading.x == 0 && wantedHeading.y == 1) {
                backDirection = down;
            } else {
                if (wantedHeading.x == -1 && wantedHeading.y == 0) {
                    backDirection = left;
                } else {
                    backDirection = up;
                }
            }
        }
        // opposite direction
        if (Math.abs(backDirection - currentDirection) == 2) {
            actionPool.add(new AnAction("turn", "right"));
            actionPool.add(new AnAction("turn", "right"));
        } else {
            if ((backDirection - currentDirection) == 1 || (backDirection - currentDirection) == -3) {
                actionPool.add(new AnAction("turn", "right"));
            } else {
                actionPool.add(new AnAction("turn", "left"));
            }
        }
        return backDirection;
    }
}
