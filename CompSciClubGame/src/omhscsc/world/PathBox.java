package omhscsc.world;

import java.awt.Color;
import java.util.List;

import omhscsc.Game;
import omhscsc.state.GameStateState;
import omhscsc.util.Hitbox;
import omhscsc.util.Location;
import omhscsc.util.Velocity;

public class PathBox extends WorldObject {

	protected List<Location>path;
	private Velocity currentVelocity;
	protected int current,next,last;
	protected double speed;
	protected final float error = 1f;
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
	public PathBox(int x, int y, int w, int h, Color c, List<Location> travelPath, double speed) {
		super(x, y, w, h, c);
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
		if(currentVelocity.getX() == 0 && currentVelocity.getY() == 0)
			findVelocity();
		Hitbox h = super.getHitbox();
		h.addX((currentVelocity.getX()/Game.TPS)* Game.getTimeRate());
		h.addY((currentVelocity.getY()/Game.TPS)* Game.getTimeRate());
		super.changeHitbox(h);
		Hitbox t = new Hitbox(h.getBounds().width,h.getBounds().height,h.getLocation());
		t.addY(-1); //covers bottom pixel of player
		if (s.getPlayer().getHitbox().getBounds().intersects(t.getBounds())) {//player is touching box
			s.getPlayer().getHitbox().addX((currentVelocity.getX()/Game.TPS) * Game.getTimeRate());
			s.getPlayer().getHitbox().addY((currentVelocity.getY()/Game.TPS) * Game.getTimeRate());
		}
		
		if(path.get(current).getX() - path.get(next).getX() <= error && path.get(current).getY() - path.get(next).getY() <= error){
			toNext();
		}
	}
	
	private void findVelocity() {
		//Cannot exceed speed.
		double xv = 0;
		double yv = 0;
		double xdist = (path.get(current).getX() - this.getHitbox().getLocation().getX());
		double ydist = (path.get(current).getY() - this.getHitbox().getLocation().getY());
		double angle = Math.atan(ydist/xdist);
		xv = this.speed * Math.cos(angle);
		yv = this.speed * Math.sin(angle);
		//This might not work for everything
		//Not finished
	}

	private void toNext() {
		last = current;
		current = next;
		next = getNext();
	}
	
	public int getNext() {
		int c = current+1;
		if(c >= path.size())
			return 0;
		return c;
	}
	
	
}
