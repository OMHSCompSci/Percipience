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
import omhscsc.graphic.animation.Animation;
import omhscsc.graphic.animation.LightAnimation;
import omhscsc.sound.Sound;
import omhscsc.sound.SoundMaster;
import omhscsc.ui.UIButton;
import omhscsc.util.ImageLoader;

public class MainMenuState extends GameState {

	private UIButton[] buttons;
	private ButtonConfig[] buttonLocs;
	private BufferedImage bg,button,button_invert;
	private int selected;
	private Animation lightAnimation;
	
	class ButtonConfig {
		
		private int x,y,w,h,textX,textY;
		
		ButtonConfig(int x, int y, int w, int h, int textX, int textY)
		{
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.textX = textX;
			this.textY = textY;
		}
		
		public int getX()
		{
			return this.x;
		}
		
		public int getY()
		{
			return this.y;
		}
		
		public int getW()
		{
			return this.w;
		}
		
		public int getH()
		{
			return this.h;
		}
		
		public int getTextX()
		{
			return this.textX;
		}
		
		public int getTextY()
		{
			return this.textY;
		}
		
	}
	
	
	public MainMenuState(final UIButton[] buttons, Game g)
	{
		super(g);
		this.buttons = buttons;
		selected = -1;
		bg = ImageLoader.loadImage("gamebg");
		button = ImageLoader.loadImage("button");
		button_invert = ImageLoader.loadImage("button_invert");
		lightAnimation = new LightAnimation(10);
	}
	
	public void calculateButtonLocations(Graphics g)
	{
		if(buttons == null)
			return;
		if(buttons.length == 0)
			return;
		buttonLocs = new ButtonConfig[buttons.length];
		//Should do this every time new button is added / removed
		int ydiv = (int)((Game.getHeight() - Game.getHeight()*.6 - (5*buttons.length))/buttons.length);
		int xdiv = (int)(Game.getWidth()*0.5);
		int xoff = 0;
		int yoff = 0;
		int ybase = (int)(Game.getHeight()*0.3);
		//Draw bg
		for(int i = 0; i<buttons.length;i++)
		{
			yoff = ybase + (i * ydiv) + (10*i);
			if(selected != i)
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
			int textX = xoff+(int)(xdiv/2)-(int)(tl.getBounds().getWidth()/2);
			int textY = 8+yoff+(int)(ydiv/2)-(int)(tl.getBounds().getHeight())/2;
			buttonLocs[i] = new ButtonConfig(xoff,yoff, xdiv,ydiv, textX, textY);
		}
	}
	
	/*
	@Override
	public void render(Graphics g) {
		int ydiv = (int)((Game.getHeight() - Game.getHeight()*.6 - (5*buttons.length))/buttons.length);
		int xdiv = (int)(Game.getWidth()*0.5);
		int xoff = 0;
		int yoff = 0;
		int ybase = (int)(Game.getHeight()*0.3);
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
			/*g.setColor(Color.gray);
		//	g.drawRect(xoff, yoff, xdiv, ydiv);
			//System.out.println(ydiv);
			TextLayout tl = new TextLayout(buttons[i].getTitle(), new Font("Monaco", Font.PLAIN, (int)(64 * (ydiv/283.0))), ((Graphics2D)g).getFontRenderContext());
			tl.draw(((Graphics2D)g), xoff+(int)(xdiv/2)-(int)(tl.getBounds().getWidth()/2), 8+yoff+(int)(ydiv/2)-(int)(tl.getBounds().getHeight())/2);
		}
		g.setColor(last);
	}*/
	
	@Override
	public void render(Graphics g) {
		if(this.buttonLocs == null)
			this.calculateButtonLocations(g);
		Color last = g.getColor();
		//Draw bg
		g.drawImage(bg, 0, 0, Game.getWidth(), Game.getHeight(), null);
		lightAnimation.renderAndIncrement(g, xo, yo, scale);
		for(int i = 0; i<buttons.length;i++)
		{
			ButtonConfig bc = buttonLocs[i];
			int xoff = bc.getX();
			int yoff = bc.getY();
			int xdiv = bc.getW();
			int ydiv = bc.getH();
			int textX = bc.getTextX();
			int textY = bc.getTextY();
			int expandX = 10;
			int expandY = 20;
			if(selected != i)
				g.drawImage(button, xoff, yoff, xdiv, ydiv, null);
			else
				g.drawImage(button, xoff, yoff-(expandY/2), xdiv+expandX, ydiv+expandY, null);

			g.setColor(Color.gray);
			TextLayout tl = new TextLayout(buttons[i].getTitle(), new Font("Monaco", Font.PLAIN, (int)(64 * (ydiv/283.0))), ((Graphics2D)g).getFontRenderContext());
			tl.draw(((Graphics2D)g), textX, textY);
		}
		g.setColor(last);
	}
	
	public Rectangle getButtonBox(int index)
	{
		int ydiv = (int)((Game.getHeight() - Game.getHeight()*.6 - (5*buttons.length))/buttons.length);
		int xdiv = (int)(Game.getWidth()*0.5);
		int xoff = 0;
		int yoff = 0;
		int ybase = (int)(Game.getHeight()*0.3);
		yoff = ybase + (index * ydiv) + (10 * index);
		return new Rectangle(xoff,yoff,xdiv,ydiv);
	}

	
	
	@Override
	public void tick() {
		if(!SoundMaster.isSoundPlaying(Sound.TITLE))
			SoundMaster.playSound(Sound.TITLE);
		lightAnimation.tick();
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
				if(selected != i)
					SoundMaster.playSound(Sound.BEEP);
				selected=i;
				return;
			}
		}
		selected = -1;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			if(selected <= 0)
				selected = this.buttons.length-1;
			else
				selected--;
			SoundMaster.playSound(Sound.BEEP);
			return;
		}
		if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(selected >= this.buttons.length-1)
				selected = 0;
			else
				selected++;
			SoundMaster.playSound(Sound.BEEP);
			return;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(selected >= 0)
				buttons[selected].clicked(this);
		}
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
		SoundMaster.stopSound(Sound.TITLE);
	}

	@Override
	public void gainedFocus() {
		
	}

	
	
}
