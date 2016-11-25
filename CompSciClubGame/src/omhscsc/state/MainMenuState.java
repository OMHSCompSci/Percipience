package omhscsc.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

import omhscsc.Game;
import omhscsc.sound.Sound;
import omhscsc.sound.SoundMaster;
import omhscsc.ui.UIButton;
import omhscsc.util.ImageLoader;

public class MainMenuState extends GameState {

	private UIButton[] buttons;
	private BufferedImage bg,button,button_invert;
	private int hovered;
	
	public MainMenuState(final UIButton[] buttons, Game g)
	{
		super(g);
		this.buttons = buttons;
		hovered = -1;
		bg = ImageLoader.loadImage("gamebg.png");
		button = ImageLoader.loadImage("button.png");
		button_invert = ImageLoader.loadImage("button_invert.png");
	}
	
	@Override
	public void render(Graphics g) {
		int ydiv = (int)((Game.HEIGHT - Game.HEIGHT*.6 - (5*buttons.length))/buttons.length);
		int xdiv = (int)(Game.WIDTH*0.5);
		int xoff = 0;
		int yoff = 0;
		int ybase = (int)(Game.HEIGHT*0.3);
		Color last = g.getColor();
		//Draw bg
		g.drawImage(bg, 0, 0, bg.getWidth(), bg.getHeight(), null);
		for(int i = 0; i<buttons.length;i++)
		{
			yoff = ybase + (i * ydiv) + (10*i);
			if(hovered != i)
				g.drawImage(button, xoff, yoff, xdiv, ydiv, null);
			else
				g.drawImage(button_invert, xoff, yoff, xdiv, ydiv, null);
/*
			if(hovered != i)
				g.setColor(Color.gray);
			else
				g.setColor(Color.WHITE);
			g.fillRect(xoff, yoff, xdiv, ydiv);
*/
			g.setColor(Color.gray);
		//	g.drawRect(xoff, yoff, xdiv, ydiv);
			//System.out.println(ydiv);
			TextLayout tl = new TextLayout(buttons[i].getTitle(), new Font("Monaco", Font.PLAIN, (int)(64 * (ydiv/283.0))), ((Graphics2D)g).getFontRenderContext());
			tl.draw(((Graphics2D)g), xoff+(int)(xdiv/2)-(int)(tl.getBounds().getWidth()/2), 8+yoff+(int)(ydiv/2)-(int)(tl.getBounds().getHeight())/2);
		}
		g.setColor(last);
	}
	
	public Rectangle getButtonBox(int index)
	{
		int ydiv = (int)((Game.HEIGHT - Game.HEIGHT*.6 - (5*buttons.length))/buttons.length);
		int xdiv = (int)(Game.WIDTH*0.5);
		int xoff = 0;
		int yoff = 0;
		int ybase = (int)(Game.HEIGHT*0.3);
		yoff = ybase + (index * ydiv) + (10 * index);
		return new Rectangle(xoff,yoff,xdiv,ydiv);
	}

	
	
	@Override
	public void tick() {
		if(!SoundMaster.isSoundPlaying(Sound.TITLE))
			SoundMaster.playSound(Sound.TITLE);
			
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (int i = 0; i < buttons.length; i++)
		{
			if(getButtonBox(i).contains(e.getPoint()))
			{
				//Do thing
				buttons[i].clicked(this);
				System.out.println(i + " was clicked");
				return;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (int i = 0; i < buttons.length; i++)
		{
			if(getButtonBox(i).contains(e.getPoint()))
			{
				hovered=i;
				return;
			}
		}
		hovered = -1;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lostFocus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gainedFocus() {
		// TODO Auto-generated method stub
		
	}

	
	
}
