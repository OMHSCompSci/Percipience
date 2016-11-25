package omhscsc.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import omhscsc.Camera;
import omhscsc.Game;
import omhscsc.GameObject;
import omhscsc.entities.Player;
import omhscsc.graphic.Renderable;
import omhscsc.util.Anchor;
import omhscsc.util.Hitbox;
import omhscsc.util.Location;
import omhscsc.util.Task;
import omhscsc.world.World;
import omhscsc.world.WorldObject;

public class GameStateState extends GameState {

	private Set<GameObject>go;
	private Set<Renderable>re;
	private Camera camera;
	private World currentWorld;
	private Player player;
	
	public GameStateState(Game g)
	{
		super(g);
		go = new HashSet<GameObject>();
		re = new HashSet<Renderable>();
		currentWorld = World.getWorld(0);
		camera = new Camera(new Location(0,0,currentWorld), Game.WIDTH, Game.HEIGHT);
		player = new Player("Freddy",new Hitbox(70, 70,new Location(0,-50,currentWorld)));
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
		re.remove(r);
	}
	
	public void removeGameObject(GameObject g)
	{
		go.remove(g);
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
			HashSet<Task> completedTasks = new HashSet<Task>();
			for (GameObject g : go)
			{
				g.tick(this);
				if(g instanceof Task)
				{
					if(((Task) g).isTaskComplete())
					{
						completedTasks.add((Task)g);
					}
				}
			}
			for(Task t : completedTasks)
				go.remove(t);
			
			completedTasks.clear();
			completedTasks = null;
			//This shouldn't be needed because every time this method ends all local variables are garbage collected I think?
			camera.tick(this);
			currentWorld.tick(this);
		} catch (ConcurrentModificationException e)
		{
			//When you don't want to deal
			//Hopefully Tasks should make this unneeded. Input should ==NEVER== directly add a game object 
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

	public Set<GameObject> getGameObjects() {
		return go;
	}
	public void win(){
		JOptionPane.showMessageDialog(null, "You Win!");
		this.getGame().setGameState(0);
	}

	@Override
	public void lostFocus() {
		//Player quit
	}

	@Override
	public void gainedFocus() {
		//Player started
		//Use this to renitialize the world and other things
	}
}
