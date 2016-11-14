package omhscsc.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class World {

	private final int id;
	private List<WorldObject> wo;
	
	public World(int id)
	{
		this.id = id;
		wo = new ArrayList<WorldObject>();
	}
	
	public List<WorldObject> getWorldObjects()
	{
		return wo;
	}
	
	public void addWorldObject(WorldObject w)
	{
		this.wo.add(w);
	}
	
	public int getId()
	{
		return this.id;
	}
	
	private static List<World> worlds;
	private static boolean initialized = false;
	
	public static void init()
	{
		if(initialized)
			return;
		worlds = new ArrayList<World>();
		
		World world1 = new World(0);
		world1.addWorldObject(new WorldObject(world1, 0,20,400,80, Color.RED));
		world1.addWorldObject(new WorldObject(world1, 400,-20,200,120,Color.RED));
		world1.addWorldObject(new WorldObject(world1, -900,-900,900,1000, Color.RED));
		world1.addWorldObject(new WorldObject(world1, 600,20,100,80, Color.RED));
		world1.addWorldObject(new WorldObject(world1, 700,90, 800, 10, Color.RED));
		world1.addWorldObject(new WorldObject(world1, 900, -20, 400, 10, Color.RED));
		world1.addWorldObject(new WorldObject(world1, 1500,20,600,80, Color.RED));
		world1.addWorldObject(new WorldObject(world1, 2300, -20, 400, 10, Color.RED));
		world1.addWorldObject(new WorldObject(world1, 2900,20,600,80, Color.RED));
		world1.addWorldObject(new WorldObject(world1, 3500,-900,900,1000, Color.RED));
		
		
		World world2 = new World(1);
		
		World world3 = new World(2);
		
		worlds.add(world1);
		worlds.add(world2);
		worlds.add(world3);
		initialized = true;
	}
	
	public static World getWorld(int index)
	{
		if(!initialized)
			return null;
		return worlds.get(index);
	}
	
	
}
