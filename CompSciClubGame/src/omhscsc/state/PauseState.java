package omhscsc.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

import omhscsc.Game;

public class PauseState extends GameState{
	private Game savedGame;
	
	private boolean focus;
	public PauseState(Game g) {
		super(g);
		this.savedGame = g;
		// TODO Auto-generated constructor stub
	}
	
	class pauseScreen(){
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(this.focus){
			JFrame.
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lostFocus() {
		this.focus = false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gainedFocus() {
		this.focus = true;
		// TODO Auto-generated method stub
		
	}

}
