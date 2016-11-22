package omhscsc.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import omhscsc.Camera;
import omhscsc.Game;
import omhscsc.GameObject;
import omhscsc.entities.Enemy;
import omhscsc.entities.NormalEnemy;
import omhscsc.entities.Player;
import omhscsc.graphic.Renderable;
import omhscsc.util.Anchor;
import omhscsc.util.Hitbox;
import omhscsc.util.Location;
import omhscsc.world.World;
import omhscsc.world.WorldObject;

public class GameStateState extends GameState {

	private List<GameObject>go;
	private List<Renderable>re;
	private Camera camera;
	private World currentWorld;
	private Player player;
	private ArrayList<Enemy> enemies;
	
	public GameStateState(Game g)
	{
		super(g);
		go = new LinkedList<GameObject>();
		re = new LinkedList<Renderable>();
		currentWorld = World.getWorld(0);
		camera = new Camera(new Location(0,0,currentWorld), Game.WIDTH, Game.HEIGHT);
		player = new Player("Freddy",new Location(0,-50,currentWorld));
		enemies = new ArrayList<Enemy>();
		enemies.add(new NormalEnemy(100, false, new Hitbox(100, 100, 500, -20, currentWorld)));
		enemies.add(new NormalEnemy(100, false, new Hitbox(100, 100, 1000, -20, currentWorld)));
		enemies.add(new NormalEnemy(100, false, new Hitbox(100, 100, 1500, -20, currentWorld)));
		enemies.add(new NormalEnemy(100, false, new Hitbox(100, 100, 2000, -20, currentWorld)));
		enemies.add(new NormalEnemy(100, false, new Hitbox(100, 100, 2500, -20, currentWorld)));
		enemies.add(new NormalEnemy(1000, true, new Hitbox(100, 100, 3000, -20, currentWorld)));
		for(Enemy e : enemies)
		{
			this.addObject(e);
		}
		addObject(player);
		camera.setAnchor((Anchor)player);
	}
	
	public void addObject(GameObject o)
	{
		if(o instanceof Renderable)
		{
			re.add((Renderable)o);
		}
		go.add(o);
	}
	
	public void addRenderable(Renderable r)
	{
		re.add(r);
	}
	
	public void removeRenderable(Renderable r)
	{
		if(re.contains(r))
			re.remove(re.indexOf(r));
	}
	
	public void removeRenederable(int index)
	{
		re.remove(index);
	}
	
	public void removeGameObject(GameObject g)
	{
		if(go.contains(g))
			go.remove(go.indexOf(g));
	}
	
	public void removeGameObject(int index)
	{
		GameObject a = go.remove(index);
		if (a instanceof Renderable)
			removeRenderable((Renderable)a);
	}
	
	@Override
	public void render(Graphics g) {
		try {
			for (WorldObject wo : currentWorld.getWorldObjects())
			{
				if(camera.intersects(wo.getHitbox()))
				{
					int xoff = (int)(wo.getHitbox().getBounds().getX() - camera.getHitbox().getBounds().getX());
					int yoff = (int)(wo.getHitbox().getBounds().getY() - camera.getHitbox().getBounds().getY());
					wo.render(g, xoff, yoff);
				}
			}
			for (Renderable r : re)
			{
				if(camera.intersects(r.getHitbox()))
				{
					int xoff = (int)(r.getHitbox().getBounds().getX() - camera.getHitbox().getBounds().getX());
					int yoff = (int)(r.getHitbox().getBounds().getY() - camera.getHitbox().getBounds().getY());
					r.render(g, xoff, yoff);
				}
			}
		} catch (ConcurrentModificationException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void tick() {
		try {
			for (GameObject g : go)
			{
				g.tick(this);
			}
			camera.tick(this);
		} catch (ConcurrentModificationException e)
		{
			//When you don't want to deal
			//e.printStackTrace();
			
			//Continue
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			player.setLeftHeld(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			player.setRightHeld(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_W)
		{
			player.jump();

		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			player.attack();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			player.setLeftHeld(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			player.setRightHeld(false);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void gameOver() {
		JOptionPane.showMessageDialog(null, "Sorry, you have died");
		this.getGame().setGameState(0);
	}
	public World getCurrentWorld(){
		return currentWorld;
	}

	public Player getPlayer() {
		return player;
	}

	public List<GameObject> getGameObjects() {
		return go;
	}
	public void win(){
		JOptionPane.showMessageDialog(null, "You Win!");
		this.getGame().setGameState(0);
	}
}
