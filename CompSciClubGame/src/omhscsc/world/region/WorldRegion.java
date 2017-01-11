package omhscsc.world.region;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import omhscsc.Game;
import omhscsc.entities.Entity;
import omhscsc.entities.LivingEntity;
import omhscsc.state.GameStateState;
import omhscsc.util.Debug;
import omhscsc.world.WorldObject;

/**
 * A world region is self explanatory. It is a region (a rectangle) of a world. The world is not included in the region, rather the region
 * is owned by the world. WorldRegions listen for different actions, such as entity enter, entity leave, entity jump, entity hurt, etc. 
 * 
 * 
 * This class will be able to be used to create safe zones, death zones, teleporters, world exits, and more.
 * @author Zach
 *
 */
public abstract class WorldRegion extends WorldObject {


	private static final long serialVersionUID = -2075588669219288682L;
	private Set<Entity> entitiesInRegion;
	private boolean drawBorder;
	
	
	public WorldRegion(int x, int y, int w, int h) {
		super(x, y, w, h, Color.WHITE);
		drawBorder = false;
		entitiesInRegion = new HashSet<Entity>();
	}

	/**
	 * Specify whether this Region should draw a border around its bounds
	 * @param b
	 */
	public void setDrawBorder(boolean b) {
		this.drawBorder = b;
	}
	
	public boolean willDrawBorder() {
		return drawBorder;
	}
	
	@Override
	public void tick(GameStateState s) {
		//Exist
		//Always call super.tick(GameStateState)
		
	}
	
	/**
	 * Inform the WorldRegion that an Entity has entered
	 * @param e
	 */
	public void entityEntered(Entity e) {
		entitiesInRegion.add(e);
	}
	
	/**
	 * Returns if the specified Entity is within the region.
	 * @param e
	 * @return
	 */
	public boolean containsEntity(Entity e) {
		return entitiesInRegion.contains(e);
	}
	
	/**
	 * Inform the WorldRegion that an Entity has left
	 * @param e
	 */
	public void entityLeft(Entity e) {
		entitiesInRegion.remove(e);
	}
	
	@Override
	public void render(Graphics g, int xo, int yo, float scale) {
		//This is needed because most regions will most likely be invisible.
		if(drawBorder)
			g.drawRect(xo, yo, (int)(this.getHitbox().getWidth() * scale), (int)(this.getHitbox().getHeight() * scale));
	}

	
	
}
