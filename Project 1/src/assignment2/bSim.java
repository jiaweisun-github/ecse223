package assignment2;

import java.awt.Color;

import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class bSim extends GraphicsProgram{

	private static final int WIDTH = 1200; // n.b. screen coords
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	private static final int NUMBALLS = 100; // # balls to sim.
	private static final double MINSIZE = 3; // Min ball size
	private static final double MAXSIZE = 20; // Max ball size
	private static final double XMIN = 10; // Min X start loc
	private static final double XMAX = 50; // Max X start loc
	private static final double YMIN = 50; // Min Y start loc
	private static final double YMAX = 100; // Max Y start loc
	private static final double EMIN = 0.1; // Min loss coeff.
	private static final double EMAX = 0.3; // Max loss coeff.
	private static final double VMIN = 0.5; // Min X velocity
	private static final double VMAX = 3.0; // Max Y velocity

	public static void main(String[]args) {

	}

	public void run() {
		RandomGenerator rgen = new RandomGenerator(); //instantiate randon generator
		this.resize(WIDTH,HEIGHT+OFFSET); // optional, initialize window size
		
		// Create the ground plane
		GRect gPlane = new GRect(0,HEIGHT,WIDTH,3);
		gPlane.setColor(Color.BLACK);
		gPlane.setFilled(true);
		add(gPlane);

		for(int i = 0; i<100; i++) {
			double Xi = rgen.nextDouble(10, 50);
			double Yi = rgen.nextDouble(50, 100);
			double bSize = rgen.nextDouble(3, 20);
			Color bColor = rgen.nextColor();
			double bLoss = rgen.nextDouble(0.1, 0.3);
			double bVel = rgen.nextDouble(0.5, 3);

			gBall ball = new gBall(Xi, Yi, bSize, bColor, bLoss, bVel);		//create an object of type gBall(constructor ball)
			add(ball.myBall);
			ball.start();
		}
		
	}
}
