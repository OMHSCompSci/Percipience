package omhscsc.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import omhscsc.Camera;
import omhscsc.Game;
import omhscsc.GameObject;
import omhscsc.RenderableGameObject;
import omhscsc.entities.Entity;
import omhscsc.state.GameStateState;
import omhscsc.util.Hitbox;
import omhscsc.util.ImageLoader;
import omhscsc.util.Location;
import omhscsc.world.object.Box;
import omhscsc.world.region.WorldRegion;

public class World extends GameObject implements Serializable {

	/*
	 * The world / world objects implement serializable because we want to be
	 * able to save and load worlds. Images are not included in the world file,
	 * and are packaged in the source foulder instead.
	 */
	
	
	//TODO: PARALLAX REPEAT
	
	
	private static final long serialVersionUID = 2397350907912764787L;
	protected final int id;
	protected String worldName;
	protected Set<RenderableGameObject> wo;
	// These are the objects in the world, not the entities

	// Sets do not preserve order
	protected Set<Entity> entitiesInWorld;
	protected BackgroundLayer[] backgroundLayers;
	protected Color bgColor;
	protected BufferedImage backgroundImage;
	protected Location[] possibleSpawnPoints;
	protected Set<WorldRegion> regions;

	/*
	 * For the parallax effect, 0 is the farthest back and the highest number is
	 * closest to the player.
	 */

	/**
	 * Create an empty world with the specified parameters.
	 * 
	 * @param id
	 *            The World id (used to get from world to world / world
	 *            identification)
	 * @param bgLayers
	 *            The amount of background layers (parallax)
	 * @param worldName
	 *            The World name
	 * @param parallaxScrollRates
	 *            The scroll rates used for the parallax effect. 1.0f means the
	 *            background moves in a 1:1 ratio with the player. A 0.5f rate
	 *            means it moves in a 1:2 (background:player) ratio.
	 */
	public World(int id, BackgroundLayer[] bgLayers, Location[] spawnPoints, Color bgColor, BufferedImage backgroundImage, String worldName) {
		this.id = id;
		wo = new HashSet<RenderableGameObject>();
		entitiesInWorld = new HashSet<Entity>();
		backgroundLayers = bgLayers;
		regions = new HashSet<WorldRegion>();
		this.worldName = worldName;
		retrieveBackgroundImages();
		this.possibleSpawnPoints = spawnPoints;
		this.bgColor = bgColor;
		this.backgroundImage = backgroundImage;
	}

	public String getWorldName() {
		return this.worldName;
	}

	/**
	 * Get a set of all the regions in this world
	 * 
	 * @return
	 */
	public Set<WorldRegion> getWorldRegions() {
		return this.regions;
	}

	/**
	 * Removes a World region from the world
	 * 
	 * @param wr
	 *            Region removed
	 */
	public void removeWorldRegion(WorldRegion wr) {
		this.regions.remove(wr);
	}

	/**
	 * Adds a World region to the world
	 * 
	 * @param wr
	 *            Region added
	 */
	public void addWorldRegion(WorldRegion wr) {
		this.regions.add(wr);
	}

	/**
	 * Retrieves background images for the world
	 */
	private void retrieveBackgroundImages() {
		BufferedImage[] backgroundImages = ImageLoader.loadWorldBackground(this.worldName);
		for(BackgroundLayer bg : backgroundLayers) {
			bg.setImage(backgroundImages[bg.getLayerNumber()]);
		}
	}

	/**
	 * Get the spawn points for this world
	 * 
	 * @return
	 */
	public Location[] getSpawnPoints() {
		return this.possibleSpawnPoints;
	}

	/**
	 * Get the game objects in the world.
	 * 
	 * @return A Set of game objects
	 */
	public Set<RenderableGameObject> getGameObjects() {
		return wo;
	}

	/**
	 * Add a game object to the world
	 * 
	 * @param w
	 */
	public void addGameObject(RenderableGameObject w) {
		this.wo.add(w);
	}

	public int getId() {
		return this.id;
	}

	/**
	 * Render the parallax background of the world. (This is done first b/c it
	 * is in the background.) (0,0) is the center of the images.
	 * 
	 * @param g
	 *            The Graphics being used
	 * @param hitbox
	 *            The location of the player
	 */
	public void renderBackground(Graphics g, Hitbox hitbox, float scale) {
		// Based on where the player is, the background should be drawn using
		// the scroll rates (somehow)
		/*
		 * NOTE:
		 * Scaling background is important
		 */
		Color lastColor = g.getColor();
		g.setColor(this.bgColor);
		g.fillRect(0, 0, Game.getWidth(), Game.getHeight());
		if(backgroundImage != null)
			g.drawImage(backgroundImage, 0, 0, Game.getWidth(), Game.getHeight(), null);
		for (int i = 0; i < backgroundLayers.length; i++) {
			if (backgroundLayers == null)
				return;
			BufferedImage bi = backgroundLayers[i].getImage();
			if (bi == null)
				continue;
			float scrollRate = backgroundLayers[i].getScrollRate();
			//Attempt to set the width easier
			int imgX = (int) (hitbox.getBounds().getCenterX() * scrollRate - ((Game.getWidth() / scale) / 2)) + (int) (bi.getWidth() / 2) - (int) (hitbox.getWidth() / 2 * scale);
			int imgY = (int) (hitbox.getBounds().getCenterY() * scrollRate - ((Game.getHeight() / scale) / 2)) + (int) (bi.getHeight() / 2) - (int) (hitbox.getHeight() / 2 * scale);
			int imgX2 = (int) (imgX + (Game.getWidth() /scale)) + (int) (bi.getWidth() / 2) + (int) (hitbox.getWidth() / 2 * scale);
			int imgY2 = (int) (imgY + (Game.getHeight() /scale)) + (int) (bi.getHeight() / 2) + (int) (hitbox.getHeight() / 2 * scale);
			g.drawImage(bi, 0, 0, Game.getWidth(), Game.getHeight(), imgX, imgY, imgX2, imgY2, null);
			// bi = image drawn
			// first two parameters are where to start drawing the rectangle in
			// the GAME WINDOW
			// the second two parameters are where it ends. This is just saying
			// draw to the game window.
			// The third two parameters are where in the image the player is,
			// and the final two finish the rectangle.
		}
		g.setColor(lastColor);
	}

	/**
	 * Adds an Entity to this world. If it exists in the world already, there
	 * will be no change.
	 * 
	 * @param e
	 *            The entity
	 */
	public void addEntity(Entity e) {
		entitiesInWorld.add(e);
	}

	/**
	 * Removes an Entity from the world.
	 * 
	 * @param e
	 *            Entity removed
	 */
	public void removeEntity(Entity e) {
		entitiesInWorld.remove(e);
	}

	@Override
	public void tick(GameStateState s) {

		for (RenderableGameObject w : wo) {
			w.tick(s);
		}
		// This will be useful for when the worlds have projectile's and become
		// more complex in general.
		for (Entity e : entitiesInWorld) {
			e.tick(s);
			for(WorldRegion wr : regions) {
				if(wr.containsEntity(e)) {
					if(!wr.getHitbox().getBounds().intersects(e.getHitbox().getBounds())) {
						wr.entityLeft(e);
						continue;
					}
				} else {
					if(wr.getHitbox().getBounds().intersects(e.getHitbox().getBounds())) {
						wr.entityEntered(e);
					}
				}
			}
		}
		for(WorldRegion wr : regions) {
			wr.tick(s);
		}
	}

	public Set<Entity> getEntitiesInWorld() {
		return this.entitiesInWorld;
	}
	

	/**
	 * Render the world, including its entities and world objects.
	 */
	public void render(Graphics g, Camera camera, float scale) {
		renderBackground(g, camera.getHitbox(), scale);
		for (RenderableGameObject wo : this.getGameObjects()) {
			if (camera.intersects(wo.getHitbox())) {
				int xoff = (int) ((wo.getHitbox().getBounds().getX() - camera.getHitbox().getBounds().getX()) * scale);
				int yoff = (int) ((wo.getHitbox().getBounds().getY() - camera.getHitbox().getBounds().getY()) * scale);
				wo.render(g, xoff, yoff, scale);
			}
		}
		for (Entity wo : this.getEntitiesInWorld()) {
			if (camera.intersects(wo.getHitbox())) {
				int xoff = (int) ((wo.getHitbox().getBounds().getX() - camera.getHitbox().getBounds().getX()) * scale);
				int yoff = (int) ((wo.getHitbox().getBounds().getY() - camera.getHitbox().getBounds().getY()) * scale);
				wo.render(g, xoff, yoff, scale);
			}
		}
		for(WorldRegion wo : regions) {
			if (camera.intersects(wo.getHitbox())) {
				int xoff = (int) ((wo.getHitbox().getBounds().getX() - camera.getHitbox().getBounds().getX()) * scale);
				int yoff = (int) ((wo.getHitbox().getBounds().getY() - camera.getHitbox().getBounds().getY()) * scale);
				wo.render(g, xoff, yoff, scale);
			}
		}
		
	}

	//////////////////////

	private static List<World> worlds;
	private static boolean initialized = false;

	public static void init() {
		if (initialized)
			return;
		worlds = new ArrayList<World>();
		BackgroundLayer[] bgl = new BackgroundLayer[2];
		bgl[0] = new BackgroundLayer(0, null, 0.2f, true);
		bgl[1] = new BackgroundLayer(1, null, 0.3f, true);
		World startingWorld = new World(0, bgl, new Location[] {new Location(0,-110)}, Color.black, ImageLoader.loadWorldBackground("starting_world")[0], "starting_world");
		startingWorld.addGameObject(new Box(-1000,10,2000,800, "dark_grass"));
		
		
		worlds.add(startingWorld);
		// World startWorld = new World(id, id, parallaxScrollRates,
		// possibleSpawnPoints, worldName);
		initialized = true;
	}

	public static World getWorld(int index) {
		if (!initialized)
			return null;
		return worlds.get(index);
	}


}
