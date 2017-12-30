package omhscsc.world.designer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import omhscsc.Camera;
import omhscsc.util.Location;
import omhscsc.world.World;

public class WorldDesignerGUI extends JFrame {
	
	/*
	 * To do:
	 * Create JFrame
	 * render edited world to jframe directly
	 * allow zoom in / out
	 * be able to drag and drop the different types of world objects into the world, and be able to enter width and height and x and y values manually
	 * be able to create rectangles with the mouse to create WorldRegion's.
	 * be able to move camera location around too
	 * 
	 * Available resources to look at and use:
	 * World.java
	 * Camera.java
	 * WorldObject.java (and its sub classes)
	 * Anchor.java
	 * Hitbox.java
	 * Location.java
	 * RenderableGameObject.java
	 * GameObject.java
	 * Game.java (look at the window creation and stuff)
	 * GameStateState.java (render methods)
	 * 
	 * Also look up:
	 * JFrame documentation (google)
	 * MouseAdapter documentation (google)
	 * KeyAdapter documentation (google)
	 * 
	 */

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1359513068562831490L;



	public static void main(String[] args) {
		
	}
	
	
	private World currentWorld;
	private Camera camera;
	
	
	
	public WorldDesignerGUI() {
		this.setSize(1600,900);
		this.setResizable(false);
		this.setTitle("Percipience World Designer");
		this.setUndecorated(true);
		this.addKeyListener(new GUIKeyAdapter(this));
		camera = new Camera(new Location(0,-100), 1600,900);
		currentWorld = null;
	}
	
	
	@Override
	public void paint(Graphics g) {
		//Draw world
		//Draw hud stuff over world
		if(currentWorld == null) {
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.white);
			g.drawString("Open or create a world", 200, 200);
		} else {
			currentWorld.render(g, camera, 1.0f);
		}
	}
	
	
	
	
	class GUIKeyAdapter extends KeyAdapter {
		
		private WorldDesignerGUI g;
		
		public GUIKeyAdapter(WorldDesignerGUI g) {
			this.g = g;
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_N && e.isControlDown()) {
				//Make new world
				currentWorld = new World(defaultCloseOperation, defaultCloseOperation, null, null, null, preserveBackgroundColor, null, title);
			}
		}
	}
}
