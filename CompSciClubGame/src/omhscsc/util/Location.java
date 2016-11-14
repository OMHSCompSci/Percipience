package omhscsc.util;

import omhscsc.world.World;

public class Location implements Anchor {

	private double x, y;
	private World w;
	
	public Location(double x, double y, World w)
	{
		this.x = x;
		this.y = y;
		this.w = w;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void addX(double x)
	{
		this.x+=x;
	}
	
	public void addY(double y)
	{
		this.y+=y;
	}
	
	public Location clone()
	{
		return new Location(x,y,w);
	}
	
	public Location getLocation()
	{
		return this;
	}

	public World getWorld() {
		return w;
	}

	
	
	
}
