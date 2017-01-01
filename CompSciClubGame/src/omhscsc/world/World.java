package omhscsc.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import omhscsc.Game;
import omhscsc.GameObject;
import omhscsc.RenderableGameObject;
import omhscsc.entities.NormalEnemy;
import omhscsc.state.GameStateState;
import omhscsc.util.Hitbox;
import omhscsc.util.ImageLoader;
import omhscsc.util.Location;

public class World extends GameObject {

	private final int id;
	private String worldName;
	private List<RenderableGameObject> wo;
	private transient BufferedImage[] backgroundLayers;
	private float[] parallaxScrollRates;
	private Location[] possibleSpawnPoints;
	
	
	/*
	 * For the parallax effect, 0 is the farthest back and the highest number is closest to the player.
	 */
	
	
	/**
	 * 
	 * @param id The World id (used to get from world to world / world identification)
	 * @param bgLayers The amount of background layers (parallax)
	 * @param worldName The World name
	 * @param parallaxScrollRates The scroll rates used for the parallax effect. 1.0f means the background moves in a 1:1 ratio with the player. A 0.5f rate means it moves in a 1:2 (background:player) ratio.
	 */
	public World(int id, int bgLayers, float[] parallaxScrollRates, Location[] spawnPoints, String worldName)
	{
		this.id = id;
		wo = new ArrayList<RenderableGameObject>();
		backgroundLayers = new BufferedImage[bgLayers];
		this.worldName = worldName;
		retrieveBackgroundImages();
		this.parallaxScrollRates = parallaxScrollRates;
		this.possibleSpawnPoints = spawnPoints;
		
	}
	
	public String getWorldName() {
		return this.worldName;
	}
	
	private void retrieveBackgroundImages() {
		backgroundLayers = ImageLoader.loadWorldBackground(this.worldName);
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
		for (int i = 0; i < backgroundLayers.length; i++) {
			if(backgroundLayers == null)
				return;
			BufferedImage bi = backgroundLayers[i];
			if(bi == null)
				continue;
			float scrollRate = parallaxScrollRates[i];
			int imgX = (int)(hitbox.getBounds().getCenterX() * scrollRate);
			int imgY = (int)(hitbox.getBounds().getCenterY() * scrollRate);
			g.drawImage(bi, 0, 0, Game.WIDTH, Game.HEIGHT, imgX, imgY, imgX + Game.WIDTH, imgY + Game.HEIGHT, null);
			//bi = image drawn
			//first two parameters are where to start drawing the rectangle in the GAME WINDOW
			//the second two parameters are where it ends. This is just saying draw to the game window.
			//The third two parameters are where in the image the player is, and the final two finish the rectangle.
		}
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
		worlds = new ArrayList<World>();
		World testWorld = new World(0, 1, new float[] {0.1f, 0.3f, .6f}, new Location[] {new Location(100,-100)}, "starting_world");
		testWorld.addGameObject(new Box(-1000, 0, 2000, 10, Color.RED));
		worlds.add(testWorld);
		initialized = true;
	}
	
	public static World getWorld(int index)
	{
		if(!initialized)
			return null;
		return worlds.get(index);
	}


}
	
	

