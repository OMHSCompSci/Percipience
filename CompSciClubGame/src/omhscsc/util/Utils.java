package omhscsc.util;

public class Utils {

	//Static helper class
	/**
	 * Round to specific place
	 * @param place 1st, 2nd, 3rd. (1st = 10^0, 2nd = 10^1, etc)
	 * @param d The double being rounded
	 * @return The rounded double
	 */
	public static double roundTo(int place, double d) {
		double deci = d - (int)d;
		if(deci == 0)
			return d;
		int v = (int)Math.pow(10, place);
		deci*=v;
		deci = (int)deci;
		deci/=v;
		return (int)d + deci;
	}
	
	/**
	 * Return the absolute distance (in a straight line) in between the two locations.
	 * @param location Location A
	 * @param location2 Location B
	 * @return The distance between the two locations
	 */
	public static double dist(Location location, Location location2) {
		double x = location.getX() - location2.getX();
		double y = location.getY() - location2.getY();
		double distSqrd = Math.pow(x, 2) + Math.pow(y, 2);
		double dist = Math.sqrt(distSqrd);
		return dist;
	}
}
