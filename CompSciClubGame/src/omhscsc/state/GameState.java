package omhscsc.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import omhscsc.Game;

public abstract class GameState {

	private Game g;
	
	public GameState(Game g)
	{
		this.g= g;
	}
	
	public Game getGame()
	{
		return this.g;
	}
	
	public abstract void render(Graphics g);
	
	public abstract void tick();
	public abstract void mouseClicked(MouseEvent e);
	public abstract void mouseMoved(MouseEvent e);
	public abstract void mouseDragged(MouseEvent e);
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);
	public abstract void mouseReleased(MouseEvent e);
	
}
