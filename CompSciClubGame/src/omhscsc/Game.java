package omhscsc;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
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
import omhscsc.util.Constants;
import omhscsc.world.World;

/**
 * The Game class contains the Game itself, and the static main method. The entire game is regulated by this class.
 *
 */
public class Game {


	//Test
	//This is for faster loading...set to true if you want sound. I don't think it makes much of a difference however
	public static final boolean sound = true;
	//Is the game running
	private boolean running, displaySmallInfo;
	//Display small info refers to FPS, player pos, and TPS. It can be more later
	//The canvas in which the game is rendered to;
	private Canvas c;
	//The Jframe which holds the canvas
	private JFrame frame;
	//The list of GameStates (see GameState.java and omhscsc.state)
	private List<GameState>states;
	//The rate at which things are affected in the game (independent of tick speed, but affects velocity...etc)
	private static float timeRate = 1f;
	/*
	 * 0 Should always be the main menu and 1 should always be the game.
	 * The current state is the index of the state being used from the states List. (see above)
	 */
	private int currentState,currentFPS,currentTPS;
	/*
	 * The rate at which player gains downward velocity (I don't understand it's value either)
	 */
	public static final int GRAVITY = 1460;
	//Default sizes of width and height for the window
	private static int WIDTH = Constants.WORLDX, HEIGHT = Constants.WORLDY;
	
	//Get width and get height represent the size of the current window.
	public static int getWidth() {
		return Game.WIDTH;
	}
	
	public static int getHeight() {
		return Game.HEIGHT;
	}
	/**
	 * 
	 * @param f A number above 0 that represents the windpw size (resolution).
	 */
	public static void SET_RESOLUTION(float f) {
		Game.WIDTH = (int)(1600f * f);
		Game.HEIGHT = (int)(900f * f);
	}
	
	//Creates the game window
	public Game()
	{
		running = false;
		states = new ArrayList<GameState>();
		frame = new JFrame("Khanological Order");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Game.WIDTH, Game.HEIGHT);
		frame.setResizable(false);
		c = new Canvas();
		frame.add(c);
		displaySmallInfo = false;
		currentFPS = 0;
		currentTPS = 0;
	}
	

	/**
	 * Add a new GameState to the gamestate list.
	 * @param gs The game state added
	 */
	public void addState(GameState gs)
	{
		states.add(gs);
	}
	
	/**
	 * Get the GameState from the selected index.
	 * @param index The location of the state in the states List.
	 * @return The state at that index
	 */
	public GameState getGameState(int index)
	{
		return this.states.get(index);
	}
	
	/**
	 * Set the current game state. This uses the states list.
	 * @param index
	 */
	public void setGameState(int index)
	{
		int lastState = this.currentState;
		this.currentState = index;
		this.states.get(lastState).lostFocus();
		this.states.get(currentState).gainedFocus();
	}
	
	/**
	 * Set the current game state by actual GameState. If it doesn't exist in the states list, it will be added.
	 * @param s
	 */
	public void setGameState(GameState s)
	{
		int lastState = this.currentState;
		if(!states.contains(s))
			addState(s);
		currentState = states.indexOf(s);
		this.states.get(lastState).lostFocus();
		this.states.get(currentState).gainedFocus();
	}
	
	/**
	 * Start the game. This method does not end until the game is quit.
	 */
	public void start()
	{
		if(running)
			return;
		running=true;
		//Loop
		init();
		//This is to remove the lag between states. (Ex, main menu takes a while, and if you jump right in game it tries to speed the game up)
		int lastKnownState = this.currentState;
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
				//System.out.println("Ticks: " + ticks + " Frames: " + frames);
				currentFPS = frames;
				currentTPS = ticks;
				ticks=0;
				frames=0;
				nsPassed=0;
			}
			lastTime = currentTime;
			if(lastKnownState != this.currentState) {
				lastKnownState = this.currentState;
				timePassed = 0;
				//Resetting to remove lag
			}
				
		}
	}
	
	/**
	 * Render the game to the canvas by passing the Graphics from the Canvas BufferStrategy
	 */
	private void render()
	{
		BufferStrategy bs = c.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		states.get(currentState).render(g);
		if(displaySmallInfo) {
			String infoString = "";
			if(states.get(currentState).getClass() == GameStateState.class) {
				infoString+= ((GameStateState)states.get(currentState)).getPlayer().getHitbox().getLocation().toString();
			}
			infoString+= " | FPS: " + currentFPS + " | TPS: " + currentTPS;
			g.setFont(new Font("Arial",Font.BOLD,18));
			g.setColor(Color.white);
			g.drawString(infoString, 10,50);
		}
			
		g.dispose();
		bs.show();
	}
	
	/**
	 * Tick the current state.
	 */
	private void tick()
	{
		
		states.get(currentState).tick();
		
	}
	
	/**
	 * Initialize the game. (Create some components, load some resources...etc)
	 */
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
	
	/**
	 * Pass mouse moved to the current GameState
	 * @param e The MouseEvent
	 */
	public void mouseMoved(MouseEvent e)
	{
		states.get(currentState).mouseMoved(e);
	}
	
	/**
	 * Pass mouse clicked to the current GameState
	 * @param e The MouseEvent
	 */
	public void mouseClicked(MouseEvent e)
	{
		states.get(currentState).mouseClicked(e);
	}
	
	/**
	 * Pass mouse dragged to the current GameState
	 * @param e The MouseEvent
	 */
	public void mouseDragged(MouseEvent e)
	{
		states.get(currentState).mouseDragged(e);
	}
	
	/**
	 * Pass key pressed to the current GameState
	 * @param e The KeyEvent
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_F1 && e.isControlDown()) {
			this.displaySmallInfo=!this.displaySmallInfo;
			if(states.get(currentState).getClass() == GameStateState.class) {
				((GameStateState)states.get(currentState)).getPlayer().setDrawHitboxes(displaySmallInfo);
			}
				
		}
		states.get(currentState).keyPressed(e);
	}

	/**
	 * Pass key released to the current GameState
	 * @param e The KeyEvent
	 */
	public void keyReleased(KeyEvent e) {
		states.get(currentState).keyReleased(e);
	}

	/**
	 * Pass the mouse released to the current GameState
	 * @param e The MouseEvent
	 */
	public void mouseReleased(MouseEvent e) {
		states.get(currentState).mouseReleased(e);
	}
	
	//TICKS PER SECOND
	public static final int TPS = 60;
	
	/**
	 * Get the rate at which things should be moving. 1.0f is the default value.
	 * @return The rate of change.
	 */
	public static float getTimeRate() {
		return Game.timeRate;
	}
	
	/**
	 * Set the rate at which things should be moving. 1.0f is the default value
	 * @param f The rate of change
	 */
	public static void setTimeRate(float f) {
		Game.timeRate = Math.abs(f);
	}
	
	/**
	 * The main method. Launches the game.
	 * @param args Don't use this
	 */
	public static void main(String[] args)
	{
		Game game = new Game();
		
		game.start();
	}
	
	/**
	 * A class specifically built to watch for different events and pass them to the game
	 * @author xDest
	 *
	 */
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
	/**
	 * A class specifically built to watch for different events and pass them to the game
	 * @author xDest
	 *
	 */
	class KeyWatcher extends KeyAdapter {
		
		private Game g;
		
		public KeyWatcher(Game g)
		{
			this.g = g;
		}
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			//This quit is unsafe and shouldn't be done unless you don't want to save.
			if(e.getKeyCode() == KeyEvent.VK_W && e.isControlDown())
					System.exit(0);
			g.keyPressed(e);
		}
		
		@Override
		public void keyReleased(KeyEvent e)
		{
			g.keyReleased(e);
		}
	
	}
}
