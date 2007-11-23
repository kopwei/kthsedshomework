package ksu.cis.wumpus;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.applet.*;

public class GridComponent extends Applet implements ActionListener {
	protected int width, height;
	protected WumpusWorld frame;
	protected GridEnvironment environment;
	ScrollPane pane;

	int boxSize = 30, minBoxSize = 20;

/** This constructor requires a Frame and a desired size */
public GridComponent(WumpusWorld frame, GridEnvironment env, int width, int height, ScrollPane pane) {
	this.frame = frame;
	this.environment = env;
	this.width = width;
	this.height = height;
	this.pane = pane;
	enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
}
public void actionPerformed(ActionEvent event) {
	System.out.println("GridComponent sees " + event);
}
public Dimension getPreferredSize() {
	return new Dimension(width, height);
}
/** Draw the grid lines, and stuff inside. */
// Altered pictures - MW 20 Now 98
public void paint(Graphics g) {
	int xSize = environment.xSize, ySize = environment.ySize;
	// Draw the grid lines
	int xWin = this.getSize().width, yWin = this.getSize().height;
	boxSize = java.lang.Math.max(minBoxSize, java.lang.Math.min(xWin / xSize, yWin / ySize));
	for (int i = 0; i <= xSize; i++) {
		g.drawLine(i * boxSize, 0, i * boxSize, ySize * boxSize);
		g.drawLine(0, i * boxSize, xSize * boxSize, i * boxSize);
	}

	Vector objs = environment.objects;
	for (int i = 0; i < objs.size(); i++) {
		Thing obj = (Thing) objs.elementAt(i);
		Point p = obj.location;
		if (p != null) {

			if (obj instanceof Wall) {

				g.setColor(Color.gray);
				g.fillRect(p.x * boxSize + 1, p.y * boxSize + 1, boxSize - 1, boxSize - 1);
				g.setColor(Color.black);
			}

			if (obj instanceof Gold) {
				g.setColor(Color.yellow);
				int xValues[] = { p.x * boxSize + (boxSize / 2), p.x * boxSize + (boxSize * 1 / 6), p.x * boxSize + (boxSize * 5 / 6)};
				int yValues[] = { p.y * boxSize + (boxSize / 6), p.y * boxSize + (boxSize * 5 / 6), p.y * boxSize + (boxSize * 5 / 6)};
				g.fillPolygon(xValues, yValues, 3);
				g.setColor(Color.lightGray);
				g.drawPolygon(xValues, yValues, 3);
				g.drawLine(
					p.x * boxSize + (boxSize / 5),
					p.y * boxSize + (boxSize / 5),
					p.x * boxSize + (boxSize / 4),
					p.y * boxSize + (boxSize / 4));
				g.drawLine(
					p.x * boxSize + (boxSize * 4 / 5),
					p.y * boxSize + (boxSize / 5),
					p.x * boxSize + (boxSize * 3 / 4),
					p.y * boxSize + (boxSize / 4));
			}

			if (obj instanceof Pit) {
				g.setColor(Color.black);
				g.drawLine(
					p.x * boxSize + (boxSize / 4),
					p.y * boxSize + (boxSize / 4),
					p.x * boxSize + (boxSize * 3 / 4),
					p.y * boxSize + (boxSize * 3 / 4));
				g.drawLine(
					p.x * boxSize + (boxSize * 3 / 4),
					p.y * boxSize + (boxSize / 4),
					p.x * boxSize + (boxSize / 4),
					p.y * boxSize + (boxSize * 3 / 4));
				g.drawLine(
					p.x * boxSize + (boxSize / 4),
					p.y * boxSize + (boxSize / 4),
					p.x * boxSize + (boxSize / 4),
					p.y * boxSize + (boxSize * 3 / 4));
				g.drawLine(
					p.x * boxSize + (boxSize / 4),
					p.y * boxSize + (boxSize / 4),
					p.x * boxSize + (boxSize * 3 / 4),
					p.y * boxSize + (boxSize / 4));
				g.drawLine(
					p.x * boxSize + (boxSize * 3 / 4),
					p.y * boxSize + (boxSize * 3 / 4),
					p.x * boxSize + (boxSize / 4),
					p.y * boxSize + (boxSize * 3 / 4));
				g.drawLine(
					p.x * boxSize + (boxSize * 3 / 4),
					p.y * boxSize + (boxSize * 3 / 4),
					p.x * boxSize + (boxSize * 3 / 4),
					p.y * boxSize + (boxSize / 4));

			}

			if (obj instanceof Wumpus) {
				g.setColor(Color.green);
				g.fillRect(p.x * boxSize + (boxSize / 4), p.y * boxSize + (boxSize / 3), boxSize / 2, boxSize / 2);
				g.fillArc(p.x * boxSize + (boxSize / 4), p.y * boxSize + (boxSize / 4), boxSize / 2, boxSize / 3, 0, 180);
				g.setColor(Color.red);
				int x1Values[] =
					{ p.x * boxSize + (boxSize * 3 / 10), p.x * boxSize + (boxSize * 3 / 10), p.x * boxSize + (boxSize * 5 / 10)};
				int y1Values[] = { p.y * boxSize + (boxSize / 2), p.y * boxSize + (boxSize * 4 / 10), p.y * boxSize + (boxSize / 2)};
				g.fillPolygon(x1Values, y1Values, 3);
				int x2Values[] =
					{ p.x * boxSize + (boxSize * 7 / 10), p.x * boxSize + (boxSize * 7 / 10), p.x * boxSize + (boxSize * 5 / 10)};
				int y2Values[] = { p.y * boxSize + (boxSize / 2), p.y * boxSize + (boxSize * 4 / 10), p.y * boxSize + (boxSize / 2)};
				g.fillPolygon(x2Values, y2Values, 3);
				g.fillArc(
					p.x * boxSize + (boxSize * 3 / 10),
					p.y * boxSize + (boxSize * 6 / 10),
					boxSize * 4 / 10,
					boxSize / 3,
					0,
					180);
			}

			if (obj instanceof DeadWumpus) {
				g.setColor(Color.cyan);
				g.fillRect(p.x * boxSize + (boxSize / 4), p.y * boxSize + (boxSize / 3), boxSize / 2, boxSize / 2);
				g.fillArc(p.x * boxSize + (boxSize / 4), p.y * boxSize + (boxSize / 4), boxSize / 2, boxSize / 3, 0, 180);
				g.setColor(Color.red);

				g.drawLine(
					p.x * boxSize + (boxSize * 3 / 10),
					p.y * boxSize + (boxSize * 4 / 10),
					p.x * boxSize + (boxSize / 2),
					p.y * boxSize + (boxSize / 2));
				g.drawLine(
					p.x * boxSize + (boxSize / 2),
					p.y * boxSize + (boxSize * 4 / 10),
					p.x * boxSize + (boxSize * 3 / 10),
					p.y * boxSize + (boxSize / 2));

				g.drawLine(
					p.x * boxSize + (boxSize * 7 / 10),
					p.y * boxSize + (boxSize * 4 / 10),
					p.x * boxSize + (boxSize / 2),
					p.y * boxSize + (boxSize / 2));
				g.drawLine(
					p.x * boxSize + (boxSize / 2),
					p.y * boxSize + (boxSize * 4 / 10),
					p.x * boxSize + (boxSize * 7 / 10),
					p.y * boxSize + (boxSize / 2));

				g.drawArc(
					p.x * boxSize + (boxSize * 3 / 10),
					p.y * boxSize + (boxSize * 6 / 10),
					boxSize * 4 / 10,
					boxSize / 3,
					0,
					180);
			}

			if (obj instanceof AgentThing) {
				g.setColor(Color.blue);
				g.drawLine(
					p.x * boxSize + (boxSize / 2) - obj.heading.x * 6,
					p.y * boxSize + (boxSize / 2) - obj.heading.y * 6,
					p.x * boxSize + (boxSize / 2) + obj.heading.x * 12,
					p.y * boxSize + (boxSize / 2) + obj.heading.y * 12);
				g.drawLine(
					p.x * boxSize + (boxSize / 2) + obj.heading.x * 12,
					p.y * boxSize + (boxSize / 2) + obj.heading.y * 12,
					p.x * boxSize + (boxSize / 2) + obj.heading.y * 6 + obj.heading.x * 6,
					p.y * boxSize + (boxSize / 2) + obj.heading.x * 6 + obj.heading.y * 6);
				g.drawLine(
					p.x * boxSize + (boxSize / 2) + obj.heading.x * 12,
					p.y * boxSize + (boxSize / 2) + obj.heading.y * 12,
					p.x * boxSize + (boxSize / 2) - obj.heading.y * 6 + obj.heading.x * 6,
					p.y * boxSize + (boxSize / 2) - obj.heading.x * 6 + obj.heading.y * 6);
				g.setColor(Color.black);
			}

		}
	}
}
/** When the mouse moves, display where we are. */
public void processMouseMotionEvent(MouseEvent e) {
	frame.cellLabel.setText(e.getX() / boxSize + "," + e.getY() / boxSize);
	super.processMouseMotionEvent(e); // Important!
}
}
