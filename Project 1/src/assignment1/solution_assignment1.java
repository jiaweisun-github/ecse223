package assignment1;

import java.awt.Color;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

@SuppressWarnings("serial")
public class solution_assignment1 extends GraphicsProgram {

	private static final int WIDTH = 600; // n.b. screen coordinates
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	private static final int XINIT = 100; // Initial screen X location
	private static final int BRADIUS = 30; // Ball radius, in pixels
	private static final int PD = 1; // Trace point diameter
	private static final int SCALE = HEIGHT/100; // Pixels/meter
	private static final double g = 9.8; // MKS
	private static final double bSize = BRADIUS/SCALE; // Physical ball radius
	private static final double TICK = 0.1; // Clock tick duration
	private static final double MAXSIMTIME = 30; // Simulation time limit
	
	public static void main(String[]args) {
		solution_assignment1 program = new solution_assignment1();
		program.run();
	}
	public void run() {
		this.resize(WIDTH,HEIGHT+OFFSET); // optional, initialize window size
		
		// Create the ground plane
		GRect gPlane = new GRect(0,HEIGHT,WIDTH,3);
		gPlane.setColor(Color.BLACK);
		gPlane.setFilled(true);
		add(gPlane);
		
		// Create an instance of a ball
		GOval myBall = new GOval(XINIT,2*BRADIUS,2*BRADIUS,2*BRADIUS);
		myBall.setColor(Color.RED);
		myBall.setFilled(true);
		add(myBall);
		
		// Query the user for the initial height and collision loss.
		double Yi = readDouble ("Enter height the height of the ball in meters [0,100]: ");
		double loss = readDouble ("Enter energy loss parameter [0,1]: ");
		double vh = readDouble ("Enter horizontal velocity in meters/second [0,10]: ");
		
		// Move ball to starting point.
		// Note that the the Y axis is flipped vertically!
		// Coordinate conversion should be moved to a method rather than converting on
		// the fly as done below (see solutions for Assignment 2).
		// Note: Position is defined by ball center
		double Xi = XINIT/SCALE; // Simulation (Xi,Yi) in MKS coordinates
		myBall.setLocation((int)((Xi-bSize)*SCALE),
				(int)(HEIGHT-((Yi+bSize)*SCALE)));
		trace(Xi,Yi); // add trace point
		
		// Main animation loop - do this until program halted by closing window
		// Units are MKS with mass = 1.0 Kg
		double time = 0; // Simulation clock
		double total_time = 0; // Tracks time from beginning
		double vt = Math.sqrt(2*g*Yi); // Terminal velocity
		double height = Yi; // Initial height of drop
		double Xpos = Xi; // Current X position (MKS)
		double Ypos; // Current Y position (MKS)
		int dir = 0; // 0 down, 1 up
		double last_top = Yi; // Height of last drop
		double el = Math.sqrt(1.0-loss); // Energy loss scale factor for velocity
		
		// This while loop computes height:
		// dir=0: falling under gravity --> height = h0 - 0.5*g*t^2
		// dir=1: vertical projectile motion --> height = vt*t - 0.5*g*t^2
		
		while (true) {
			if (dir == 0) {
				height = last_top - 0.5*g*time*time;
				if (height <= bSize) {
					dir=1;
					last_top = bSize;
					time=0;
					vt = vt*el;
				}
			}
			else {
				height = bSize + vt*time -0.5*g*time*time;
				if (height < last_top) {
					if (height <= bSize) break; // Stop simulation when top of
					dir=0; // last excursion is ball diameter.
					time=0;
				} last_top =
						height;
			}
			
			// Update ball position on screen
			Xpos = vh*total_time;
			Ypos = Math.max(bSize, height);
			int ScrX = (int) ((Xpos-bSize)*SCALE);
			int ScrY = (int) (HEIGHT-(Ypos+bSize)*SCALE);
			myBall.setLocation(ScrX,ScrY); // Screen units
			trace(Xpos,Ypos); // Simulation (MKS) units
			pause(TICK*500); // Let display catch up
			time+=TICK; // Update time
			total_time+=TICK;
			
			// Check to see if we've exceeded simulation time.
			if (total_time >= MAXSIMTIME) break; // Stop on time exceeded.
		}
	}
	// A simple method to plot trace points on the screen
	private void trace(double x, double y) {
		int ScrX = (int) (x*SCALE);
		int ScrY = HEIGHT - (int) (y*SCALE);
		GOval pt = new GOval(ScrX,ScrY,PD,PD);
		pt.setColor(Color.BLACK);
		pt.setFilled(true);
		add(pt);
	}
}