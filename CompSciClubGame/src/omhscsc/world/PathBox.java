package omhscsc.world;

import java.awt.Color;
import java.util.List;

import omhscsc.state.GameStateState;
import omhscsc.util.Location;

public class PathBox extends WorldObject {

	protected List<Location>path;
	protected int current,next,last;
	protected double speed;
	/**
	 * PathBox is similar to {@link MovingBox}, except it stays on a specific path and moves up and down it's list of points.
	 * @param world The world it's in
	 * @param x Starting x value
	 * @param y Starting y value
	 * @param w Width
	 * @param h Height
	 * @param c Color
	 * @param travelPath The list of coordinates to travel on
	 * @param speed The speed of in pixels per second of the box
	 */
	public PathBox(World world, int x, int y, int w, int h, Color c, List<Location> travelPath, double speed) {
		super(world, x, y, w, h, c);
		path = travelPath;
		current = 0;
		next = 1;
		last = 1;
		this.speed = speed;
	}

	@Override
	public void tick(GameStateState s) {
		/*
		 * Have the Box move from the current point to the next point, also make sure
		 * it doesn't overshoot. After it reaches one point, switch to the next and change "current, next, and last"
		 */
		//Too lazy to do this right now lol
	}

	
	
}
