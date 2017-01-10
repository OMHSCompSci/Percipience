package omhscsc.world;

import java.awt.Color;
import java.awt.Graphics;

import omhscsc.state.GameStateState;

/**
 * A world region is self explanatory. It is a region (a rectangle) of a world. The world is not included in the region, rather the region
 * is owned by the world. WorldRegions listen for different actions, such as entity enter, entity leave, entity jump, entity hurt, etc. 
 * 
 * 
 * This class will be able to be used to create safe zones, death zones, teleporters, world exits, and more.
 * @author Zach
 *
 */
public class WorldRegion extends WorldObject {


	private static final long serialVersionUID = -2075588669219288682L;

	public WorldRegion(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
	}

	@Override
	public void tick(GameStateState s) {
		//Exist
	}
	
	@Override
	public void render(Graphics g, int xo, int yo, float scale) {
		//This is needed because most regions will most likely be invisible.
	}

	
	
}
