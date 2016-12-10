package omhscsc.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import omhscsc.GameObject;
import omhscsc.RenderableGameObject;
import omhscsc.entities.Enemy;
import omhscsc.entities.LivingEntity;
import omhscsc.entities.NormalEnemy;
import omhscsc.state.GameStateState;
import omhscsc.util.Location;
import omhscsc.util.geometry.Hitbox;

public class World extends GameObject {

	private final int id;
	private List<RenderableGameObject> wo;
	
	public World(int id)
	{
		this.id = id;
		wo = new ArrayList<RenderableGameObject>();
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
	
	private static List<World> worlds;
	private static boolean initialized = false;
	public static ArrayList<Integer> wobjectx = new ArrayList<Integer>();
	public static ArrayList<Integer> wobjecty = new ArrayList<Integer>();
	public static ArrayList<Integer> wobjectw = new ArrayList<Integer>();
	public static ArrayList<Integer> wobjecth = new ArrayList<Integer>();
	public static void init()
	{
		if(initialized)
			return;
		
		wobjectx.add(0);
		wobjectx.add(400);
		wobjectx.add(-900);
		wobjectx.add(600);
		wobjectx.add(700);
		wobjectx.add(900);
		wobjectx.add(1500);
		wobjectx.add(2300);
		wobjectx.add(2900);
		wobjectx.add(3500);
		
		wobjecty.add(20);
		wobjecty.add(-20);
		wobjecty.add(-900);
		wobjecty.add(20);
		wobjecty.add(90);
		wobjecty.add(-20);
		wobjecty.add(20);
		wobjecty.add(-20);
		wobjecty.add(20);
		wobjecty.add(-900);
		
		wobjectw.add(400);
		wobjectw.add(200);
		wobjectw.add(900);
		wobjectw.add(100);
		wobjectw.add(800);
		wobjectw.add(400);
		wobjectw.add(600);
		wobjectw.add(400);
		wobjectw.add(600);
		wobjectw.add(900);
		
		wobjecth.add(80);
		wobjecth.add(120);
		wobjecth.add(1000);
		wobjecth.add(80);
		wobjecth.add(10);
		wobjecth.add(10);
		wobjecth.add(80);
		wobjecth.add(10);
		wobjecth.add(80);
		wobjecth.add(1000);
		
		worlds = new ArrayList<World>();
		
		World world1 = new World(0);
		world1.addGameObject(new Box(world1, wobjectx.get(0),wobjecty.get(0),wobjectw.get(0),wobjecth.get(0), Color.RED));
		world1.addGameObject(new Box(world1, wobjectx.get(1),wobjecty.get(1),wobjectw.get(1),wobjecth.get(1),Color.BLUE));
		world1.addGameObject(new Box(world1, wobjectx.get(2),wobjecty.get(2),wobjectw.get(2),wobjecth.get(2), Color.YELLOW));
		world1.addGameObject(new Box(world1 ,wobjectx.get(3),wobjecty.get(3),wobjectw.get(3),wobjecth.get(3), Color.PINK));
		world1.addGameObject(new Box(world1, wobjectx.get(4),wobjecty.get(4),wobjectw.get(4),wobjecth.get(4), Color.GREEN));
		world1.addGameObject(new Box(world1, wobjectx.get(5),wobjecty.get(5),wobjectw.get(5),wobjecth.get(5), Color.WHITE));
		world1.addGameObject(new Box(world1, wobjectx.get(6),wobjecty.get(6),wobjectw.get(6),wobjecth.get(6), new Color(255,115,025)));
		world1.addGameObject(new Box(world1, wobjectx.get(7),wobjecty.get(7),wobjectw.get(7),wobjecth.get(7), new Color(78,225,180)));
		world1.addGameObject(new Box(world1, wobjectx.get(8),wobjecty.get(8),wobjectw.get(8),wobjecth.get(8), new Color(99,187,111)));
		world1.addGameObject(new Box(world1, wobjectx.get(9),wobjecty.get(9),wobjectw.get(9),wobjecth.get(9), new Color(69,69,69)));
		world1.addGameObject(new MovingBox(world1, 400, -10, 100,30, Color.RED, -1, 0));
		world1.addGameObject(new NormalEnemy(50,new Hitbox(30, 30, new Location(400,-100,world1))));
		
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

	@Override
	public void tick(GameStateState s) {
		for (RenderableGameObject w : wo)
		{
			w.tick(s);
		}
		//This will be useful for when the worlds have projectiles and become more complex in general. 
	}
}
	
	

