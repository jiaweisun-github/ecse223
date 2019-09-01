package assignment2;

import java.awt.Color;

import acm.graphics.GOval;

public class gBall extends Thread{

	//static variables
	private static final int WIDTH = 600; // n.b. screen coordinates
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	private static final int XINIT = 100; // Initial screen X location
	private static final int BRADIUS = 30; // Ball radius, in pixels
	private static final int PD = 1; // Trace point diameter
	private static final int SCALE = HEIGHT/100; // Pixels/meter
	private static final double G = 9.8; // MKS
	private static final double TICK = 0.1; // Clock tick duration
	private static final double MAXSIMTIME = 30; // Simulation time limit
	private static final double vh = 5;

	//instance variabes
	public GOval myBall;
	private double Xi;
	private double Yi;
	private double bSize;
	private Color bColor;
	private double bLoss;
	private double bVel;

	//constructor
	public gBall(double Xi, double Yi, double bSize, Color bColor, double bLoss, double bVel) {

		this.Xi = Xi; // Get simulation parameters
		this.Yi = Yi;
		this.bSize = bSize;
		this.bColor = bColor;
		this.bLoss = bLoss;
		this.bVel = bVel;

		myBall = new GOval(Xi, Yi, bSize, bSize);
		myBall.setFilled(true);
		myBall.setFillColor(bColor);


	}
	public void run() {

		double time = 0; // Simulation clock
		double total_time = 0; // Tracks time from beginning
		double bVel = Math.sqrt(2*G*Yi); // Terminal velocity
		double height = Yi; // Initial height of drop
		double Xpos = Xi; // Current X position (MKS)
		double Ypos; // Current Y position (MKS)
		int dir = 0; // 0 down, 1 up
		double last_top = Yi; // Height of last drop
		double el = Math.sqrt(1.0-bLoss); // Energy loss scale factor for velocity

		// This while loop computes height:
		// dir=0: falling under gravity --> height = h0 - 0.5*g*t^2
		// dir=1: vertical projectile motion --> height = bVel*t - 0.5*g*t^2

		while (true) {
			if (dir == 0) {
				height = last_top - 0.5*G*time*time;
				if (height <= bSize) {
					dir=1;
					last_top = bSize;
					time=0;
					bVel = bVel*el;
				}
			}
			else {
				height = bSize + bVel*time -0.5*G*time*time;
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

			try { // pause for 50 milliseconds
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			time+=TICK; // Update time
			total_time+=TICK;
		}
	}
}
