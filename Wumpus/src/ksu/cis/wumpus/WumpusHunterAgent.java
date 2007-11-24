package ksu.cis.wumpus;

/* fill in the methods of this class ... you may add or delete any methods
   algorithms, or data structures you need. */

// Unvisited, Visited (including breeze and smell), unsafe (including pit and wumpus)

import java.awt.Point;
import java.io.*;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;


public class WumpusHunterAgent implements AgentProgram {

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
//    private Point lastHeading = new Point();
//    private Point needHeading = new Point();
//    private int multiplicity = 0;
    private ArrayDeque<Action> actionPool = new ArrayDeque<Action>();
    private Vector<Point> arroundPoints = new Vector<Point>();
    private Vector<Point> suspiciousWumpusPoints = new Vector<Point>();
    private boolean hasArrow = true;
    private boolean isWumpusDead = false;
    private boolean isFirstSmell = true;
    private Vector<Point> pathTofirstSmellField = new Vector<Point>();

    public WumpusHunterAgent(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        xLoc = 1;
        yLoc = 1;
        gridMemory = new GridState[ySize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                gridMemory[i][j] = new GridState();
                if (i == 0 || i== xSize - 1 || j == 0 || j == ySize - 1) {
                    gridMemory[i][j].setWall(); // set the grid as wall  
                }
            }
        }
        
    }
    //Main Method

    public Action execute(Percept perceptArg, Agent agent) {
        AgentThing agentThing = (AgentThing)agent;
        if (null == perceptArg || null == agentThing) return null;
        xLoc = agentThing.location.x;
        yLoc = agentThing.location.y;
        heading = agentThing.heading;
        if (!agentTrace.isEmpty()) {
            if (agentTrace.peekLast().getLocation().equals(agentThing.location)) {
                agentTrace.removeLast();
            }
        }
        
        if (!actionPool.isEmpty()) {
            Action action = actionPool.pollFirst();
            // If the action is shoot, then the arrow is gone
            if (action.getName().equals("shoot")) {
                hasArrow = false;
            }
            return action;
        }

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
        
        WumpusPercept percept = (WumpusPercept) perceptArg;
        memorizeState(percept, agentThing);
        return (AnAction)decideAction();

//        if (percept.isGlitter) {
//            return new AnAction("grab");
//        }
//
//        if (percept.isBump) {
//            return new AnAction("turn", AIMA.randomChoice("right", "left"));
//        }
//
//        if (percept.isBreeze) {
//            if (AIMA.random() < .60) {
//                return new AnAction("turn", AIMA.randomChoice("right", "left"));
//            }
//        }
//
//        if (percept.isStench) {
//            if (AIMA.random() < .08) {
//                return new AnAction("shoot");
//            } else if (AIMA.random() < .3) {
//                return new AnAction("forward");
//            } else {
//                return new AnAction("turn", AIMA.randomChoice("right", "left"));
//            }
//        }
//
//        if (AIMA.random() < .8) {
//            return new AnAction("forward");
//        } else {
//            return new AnAction("turn", AIMA.randomChoice("right", "left"));
//        }

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
                suspiciousWumpusPoints.addElement(point);
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
            isWumpusDead = true;
            gridMemory[xLoc][yLoc].setDeadWumpus();
        }
        if (percept.isStench) {
            // If it is the first time meet the smell, store the path
            if (isFirstSmell) {
                for (AgentCoordinate agentCoo : agentTrace) {
                    pathTofirstSmellField.addElement(agentCoo.getLocation());
                }
                pathTofirstSmellField.addElement(agent.location);
                isFirstSmell = false;
            }
            // Set all the arround point as suspicious wumpus
            fillArroundPoints();
            for (Point point : arroundPoints) {
                gridMemory[point.x][point.y].setSuspiciousWumpus();
            }
        }
        if (!percept.isBreeze && !percept.isStench) {
            fillArroundPoints();
            for (Point point : arroundPoints) {
                gridMemory[point.x][point.y].setSafe();
            }
        }
        
        gridMemory[xLoc][yLoc].setVisited();
    }
    
    private void fillArroundPoints() {
        arroundPoints.clear();
        Point[] arroundPoint = {
            new Point(xLoc + 1, yLoc), 
            new Point(xLoc, yLoc + 1), 
            new Point(xLoc - 1, yLoc),
            new Point(xLoc, yLoc - 1)
        };
        if (!agentTrace.isEmpty()) {
            Point lastPosition = agentTrace.peekLast().getLocation();
            for (Point point : arroundPoint) {
                GridState gridState = gridMemory[point.x][point.y];
                if (!gridState.isWall() && !point.equals(lastPosition)) {
                    arroundPoints.addElement(point);
                }
            }
        }
        else {
            for (Point point : arroundPoint) {
                GridState gridState = gridMemory[point.x][point.y];
                if (!gridState.isWall()) {
                    arroundPoints.addElement(point);
                }
            }
        }
    }

    
    private Action decideAction() {
        GridState gs = gridMemory[xLoc][yLoc];
        if (gs.isGold()) {
            setActionsToExit();
            return new AnAction("grab");
        }
        
        fillArroundPoints();
        Vector<Point> unvisitedPoints = new Vector<Point>();
        Point wumpusPoint = new Point();
        for (int i = 0; i < arroundPoints.size(); i++) {
            Point point = arroundPoints.elementAt(i);
            // If there is wumpus, shoot it
            if (gridMemory[point.x][point.y].isWumpus() && hasArrow) {
                setHowToTurn(new Point(point.x, point.y));
                actionPool.add(new AnAction("shoot"));
                actionPool.add(new AnAction("forward"));
                wumpusPoint.x = point.x;
                wumpusPoint.y = point.y;
            }
            if (gridMemory[point.x][point.y].isUnvisited()) unvisitedPoints.add(point);
        }

        if (unvisitedPoints.size() > 0) {
            Point wantedHeading = new Point();
            Vector<Point> unexploredDirections = new Vector<Point>();
            for (int i = 0; i < unvisitedPoints.size(); i++) {
                Point point = unvisitedPoints.elementAt(i);
                unexploredDirections.add(new Point(point.x - xLoc, point.y - yLoc));
            }
            if (actionPool.size() != 0) {
                wantedHeading.x = wumpusPoint.x;
                wantedHeading.y = wumpusPoint.y;
                unexploredDirections.remove(wantedHeading);
            }
            else {
                wantedHeading.x = unexploredDirections.firstElement().x;
                wantedHeading.y = unexploredDirections.firstElement().y;
                unexploredDirections.remove(0);
                setHowToTurn(wantedHeading);
                actionPool.add(new AnAction("forward"));
            }
            
            agentTrace.add(new AgentCoordinate(xLoc, yLoc, unexploredDirections));

            AnAction actionForNow = (AnAction) actionPool.pollFirst();
            return actionForNow;
            
        }
        else {
            extremeAction();
            AnAction afn = (AnAction) actionPool.pollFirst();
            return afn;
        }
    }
    
    private void setActionsToExit() {
        moveBack(agentTrace.size());
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
            if (isWumpusDead || (!isWumpusDead && !hasArrow)) {
                // if there is unvisited field around the position where gets the first smell, go to there
                Point lastPoint = pathTofirstSmellField.lastElement();
                Point[] surroundingSmellPoints = {
                    new Point(lastPoint.x + 1, lastPoint.y),
                    new Point(lastPoint.x, lastPoint.y + 1),
                    new Point(lastPoint.x - 1, lastPoint.y),
                    new Point(lastPoint.x, lastPoint.y - 1)
                };
                for (int i = 0; i < surroundingSmellPoints.length; i++) {
                    Point point = surroundingSmellPoints[i];
                    if (gridMemory[point.x][point.y].isUnvisited()) {
                        goToFirstSmellField();
                        break;
                    }
                }
                setActionsToExit();
            }
            else { // Wumpus is not dead and has arrow
                // go to the position where gets the first smell, and shoot towards a random direction 
                // where suspects wumpus
                goToFirstSmellField();
                // surrounding points are all wumpus-suspicious fields
                fillArroundPoints();
                Random rnd = new Random(System.currentTimeMillis());
                Point taskPoint = arroundPoints.elementAt(rnd.nextInt(arroundPoints.size()));
                Point wantedHeading = new Point();
                wantedHeading.x = taskPoint.x - xLoc;
                wantedHeading.y = taskPoint.y - yLoc;
                setHowToTurn(wantedHeading);
                actionPool.add(new AnAction("shoot"));
                actionPool.add(new AnAction("forward"));
            }
        }
        else moveBack(counter);
    }
    
    // find where the wumpus is and kill it when there are no un visited and safe fileds
    private void goToFirstSmellField() {
        int counter = 0;
        // the number of steps to go back to the last same field
        int counterToGoBack = 0;
        Iterator<AgentCoordinate> it = agentTrace.iterator();
        while (it.hasNext() && counter <= pathTofirstSmellField.size()) {
            AgentCoordinate agentCoordinate = it.next();
            if (agentCoordinate.getLocation().equals(pathTofirstSmellField.elementAt(counter))) {
                counter++;
            }
            else {
                break;
            }
        }
        // plus 1 because agentTrace's last record is the last point of current position
        counterToGoBack = agentTrace.size() - counter + 1;
        moveBack(counterToGoBack);
        while (counter < pathTofirstSmellField.size()) {
            Point wantedHeading = new Point();
            wantedHeading.x = pathTofirstSmellField.elementAt(counter).x - xLoc;
            wantedHeading.y = pathTofirstSmellField.elementAt(counter).y - yLoc;
            setHowToTurn(wantedHeading);
            actionPool.add(new AnAction("forward"));
            counter++;
        }
        
    }
    
    // move back to the field whose multiplicity is not 0 when multiplicity of the current field is 0
    private void moveBack(int counter) {
        Point lastPoint = new Point();
        Point backHeading = new Point();
        backHeading.x = heading.x;
        backHeading.y = heading.y;
        int backDirection = 0;
        
        while (counter != 0) {
            AgentCoordinate ac = agentTrace.pollLast();
            lastPoint.x = ac.getX();
            lastPoint.y = ac.getY();
            backHeading.x = lastPoint.x - xLoc;
            backHeading.y = lastPoint.y - yLoc;
            
            backDirection = setHowToTurn(backHeading);
            actionPool.add(new AnAction("forward"));
            currentDirection = backDirection;
            counter--;
        }
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
        if (backDirection == currentDirection) {
            return backDirection;
        }
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
