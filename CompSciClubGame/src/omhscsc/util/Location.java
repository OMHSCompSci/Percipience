package omhscsc.util;

import omhscsc.world.World;

public class Location {

	private double x, y;
	
	public Location(double x, double y)
	{
		this.x = x;
		this.y = y;
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
		return new Location(x,y);
	}
	
	public Location getLocation()
	{
		return this;
	}


	public String toString() {
		return "X: " + Utils.roundTo(3,this.getX()) + " Y: " + Utils.roundTo(3, this.getY());
	}
	
	
	
}
