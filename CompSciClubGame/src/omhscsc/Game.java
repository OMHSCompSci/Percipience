package omhscsc;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import omhscsc.sound.SoundMaster;
import omhscsc.state.GameState;
import omhscsc.state.GameStateState;
import omhscsc.state.MainMenuState;
import omhscsc.ui.UIAction;
import omhscsc.ui.UIButton;
import omhscsc.world.World;

public class Game {


	//Test
	//This is for faster loading...set to true if you want sound. I don't think it makes much of a difference however
	public static final boolean sound = true;
	private boolean running;
	private Canvas c;
	private JFrame frame;
	private List<GameState>states;
	private static float timeRate = .1f;
	/*
	 * 0 Should always be the main menu and 1 should always be the game.
	 */
	private int currentState;
	public static final int GRAVITY = 1460;
	public static final int WIDTH = 16*80, HEIGHT = 9*80;
	
	public Game()
	{
		running = false;
		states = new ArrayList<GameState>();
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Game.WIDTH, Game.HEIGHT);
		frame.setResizable(false);
		c = new Canvas();
		frame.add(c);
	}
	
	public void addState(GameState gs)
	{
		states.add(gs);
	}
	
	public GameState getGameState(int index)
	{
		return this.states.get(index);
	}
	
	public void setGameState(int index)
	{
		this.currentState = index;
	}
	
	public void setGameState(GameState s)
	{
		if(!states.contains(s))
			addState(s);
		currentState = states.indexOf(s);
	}
	
	public void start()
	{
		if(running)
			return;
		running=true;
		//Loop
		init();
		final int nsPerTick = (int)1e9/Game.TPS;
		double timePassed = 0;
		double currentTime = System.nanoTime();
		double lastTime = System.nanoTime();
		
		double nsPassed = 0;
		int ticks = 0;
		int frames = 0;
		while(running)
		{
			currentTime = System.nanoTime();
			nsPassed += currentTime - lastTime;
			timePassed += (currentTime - lastTime)/nsPerTick;
			
			if(timePassed>=1)
			{
				//Tick!!!
				tick();
				timePassed-=1;
				ticks++;
			}
			render();
			frames++;
			
			if(nsPassed > 1e9)
			{
				System.out.println("Ticks: " + ticks + " Frames: " + frames);
				ticks=0;
				frames=0;
				nsPassed=0;
			}
			
			lastTime = currentTime;
		}
	}
	
	private void render()
	{
		BufferStrategy bs = c.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		states.get(currentState).render(g);
		g.dispose();
		bs.show();
	}
	
	private void tick()
	{
		states.get(currentState).tick();
	}
	
	private void init()
	{
		if(sound)
			SoundMaster.init();
		World.init();
		UIButton b = new UIButton("START",new UIAction() {
			@Override
			public void act(GameState a)
			{
				SwingUtilities.invokeLater(new Runnable() { 
					@Override
					public void run()
					{
						//This has to change; using saves / loads
						a.getGame().setGameState(1);
					}
				});
				
			}
		});
		UIButton b1 = new UIButton("HELP",new UIAction() {
			@Override
			public void act(GameState a)
			{
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run()
					{
						//Display help
						JOptionPane.showMessageDialog(null, "Currently Unavailable");
					}
				});
			}
		});
		UIButton b2 = new UIButton("QUIT",new UIAction() {
			@Override
			public void act(GameState a)
			{
				System.exit(0);
			}
		});
		UIButton[] list = new UIButton[] {b, b1,b2};
		MainMenuState mms = new MainMenuState(list, this);
		this.addState(mms);
		this.setGameState(mms);
		GameStateState gss = new GameStateState(this);
		this.addState(gss);
		//
			//After this, 0 is main menu and 1 is game
		//
		frame.setVisible(true);
		MouseWatcher mw = new MouseWatcher(this);
		KeyWatcher kw = new KeyWatcher(this);
		c.addKeyListener(kw);
		c.addMouseListener(mw);
		c.addMouseMotionListener(mw);
		if(c.getBufferStrategy() == null)
		{
			c.createBufferStrategy(2);
		}
	}
	
	
	public void mouseMoved(MouseEvent e)
	{
		states.get(currentState).mouseMoved(e);
	}
	
	public void mouseClicked(MouseEvent e)
	{
		states.get(currentState).mouseClicked(e);
	}
	
	public void mouseDragged(MouseEvent e)
	{
		states.get(currentState).mouseDragged(e);
	}
	
	public void keyPressed(KeyEvent e) {
		states.get(currentState).keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		states.get(currentState).keyReleased(e);
	}

	public void mouseReleased(MouseEvent e) {
		states.get(currentState).mouseReleased(e);
	}
	
	public static final int TPS = 60;
	
	public static float getTimeRate() {
		return Game.timeRate;
	}
	
	public static void setTimeRate(float f) {
		Game.timeRate = Math.abs(f);
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		
		game.start();
	}
	
	
	class MouseWatcher extends MouseAdapter {
		
		private Game g;
		
		public MouseWatcher(Game g)
		{
			this.g = g;
		}
		
		@Override
		public void mouseMoved(MouseEvent e)
		{
			g.mouseMoved(e);
		}
		
		@Override
		public void mouseClicked(MouseEvent e)
		{
			g.mouseClicked(e);
		}
		
		@Override
		public void mouseDragged(MouseEvent e)
		{
			g.mouseDragged(e);
		}
		
		@Override
		public void mouseReleased(MouseEvent e)
		{
			g.mouseReleased(e);
		}
	}
	class KeyWatcher extends KeyAdapter {
		
		private Game g;
		
		public KeyWatcher(Game g)
		{
			this.g = g;
		}
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			g.keyPressed(e);
		}
		
		@Override
		public void keyReleased(KeyEvent e)
		{
			g.keyReleased(e);
		}
	
	}
}
