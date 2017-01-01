package omhscsc.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import omhscsc.GameObject;
import omhscsc.RenderableGameObject;
import omhscsc.entities.NormalEnemy;
import omhscsc.state.GameStateState;
import omhscsc.util.Hitbox;
import omhscsc.util.Location;

public class World extends GameObject {

	private final int id;
	private List<RenderableGameObject> wo;
	private transient BufferedImage[] backgroundLayers;
	private float[] parallaxScrollRates;
	private Location[] possibleSpawnPoints;
	
	/**
	 * 
	 * @param id The World id (used to get from world to world / world identification)
	 * @param bgLayers The amount of background layers (parallax)
	 * @param worldName
	 */
	public World(int id, int bgLayers, float[] parallaxScrollRates, Location[] spawnPoints, String worldName)
	{
		this.id = id;
		wo = new ArrayList<RenderableGameObject>();
		backgroundLayers = new BufferedImage[bgLayers];
		this.parallaxScrollRates = parallaxScrollRates;
		this.possibleSpawnPoints = spawnPoints;
	}
	
	public Location[] getSpawnPoints() {
		return this.possibleSpawnPoints;
	}
	
	public List<RenderableGameObject> getGameObjects()
	{
		return wo;
	}
	
	public void addGameObject(RenderableGameObject w)
	{
		this.wo.add(w);
	}
	
	public int getId()
	{
		return this.id;
	}
	
	/**
	 * Render the parallax background of the world. (This is done first b/c it is in the background.)
	 * @param g The Graphics being used
	 * @param hitbox The location of the player
	 */
	public void renderBackground(Graphics g, Hitbox hitbox) {
		//Based on where the player is, the background should be drawn using the scroll rates (somehow)
	}
	
	@Override
	public void tick(GameStateState s) {
		for (RenderableGameObject w : wo)
		{
			w.tick(s);
		}
		//This will be useful for when the worlds have projectiles and become more complex in general. 
	}
	
	//////////////////////
	
	private static List<World> worlds;
	private static boolean initialized = false;
	
	public static void init()
	{
		if(initialized)
			return;
		
		initialized = true;
	}
	
	public static World getWorld(int index)
	{
		if(!initialized)
			return null;
		return worlds.get(index);
	}


}
	
	

