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
}
