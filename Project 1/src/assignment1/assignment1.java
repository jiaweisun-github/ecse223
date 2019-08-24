package assignment1;

import java.awt.color.*;
import javax.swing.JPanel;
import acm.graphics.GCanvas;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import java.util.*;
import java.awt.*;

public class assignment1 extends GraphicsProgram{
	
	public static final double G = 9.8;
	public static final double TIME_OUT = 30;
	public static final int SCALE = 10;
	public static final double TICK = 0.1;

	public static void main(String[]args) {
		assignment1 assignment = new assignment1();
		assignment.run();
		
	}
	
	public void run() {
		this.resize(300, 400);
		
		GRect ground = new GRect(300,3);
		ground.setColor(Color.BLACK);
		ground.setFilled(true);
		add(ground);
		
		GOval ball = new GOval(30,30,30,30);
		ball.setColor(Color.green);
		ball.setFilled(true);
		this.add(ball);
		
		double h0 = readDouble ("Enter height the height of the ball in meters [0,60]: ");		//initial height
		double vx = readDouble ("Enter horizontal speed of the ball in meters [0,60]: ");		//horizontal velocity

		// Initialize variables
		double vy = Math.sqrt(2*G*h0);		//vertical velocity
		double x;							//x_coordinate
		double y = h0;						//y_coordinate
		double last_top = h0;				//last top point
		double t = 0;						//time
		double total_time = t+=TICK;		//add every second
		boolean directionUp = false;		//direction is down in the beginning of simulation
		

		// Simulation loop
		while (total_time < TIME_OUT) {
			
			if (!directionUp) {
				
				y = h0 - 0.5*G* Math.pow(t, 2);
				if (y <= 0) {

				}
			}
		}

}
}