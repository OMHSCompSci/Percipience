package omhscsc.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import omhscsc.Game;
import omhscsc.GameObject;
import omhscsc.graphic.Renderable;
import omhscsc.items.Upgrade;
import omhscsc.items.Useable;
import omhscsc.state.GameStateState;
import omhscsc.util.Anchor;
import omhscsc.util.Hitbox;
import omhscsc.util.ImageLoader;
import omhscsc.util.Location;
import omhscsc.util.Velocity;
import omhscsc.world.WorldObject;

public class Player extends LivingEntity implements Anchor {

	private String name;
	private boolean rightHeld, leftHeld, drawDebug;
	private int healthUpgrades, speedUpgrades, jumpUpgrades;
	private Useable use;

	public Player(String n, Hitbox h) {
		super(h);
		h.setHeight(100);
		h.setWidth(50);
		name = n;
		drawDebug = false;
		healthUpgrades = 0;
		speedUpgrades = 0;
		jumpUpgrades = 0;
		health = 100.0;
		maxHealth = 100.0;
		speed = 800;
		damage = 20;
		jumpHeight = 1.25;
		rightHeld = false;
		leftHeld = false;
		velocity = new Velocity(0,0);
		use = null;
	}
	
	public void setRightHeld(boolean r)
	{
		this.rightHeld = r;
	}
	
	public void setLeftHeld(boolean r)
	{
		this.leftHeld = r;
	}
	
	public boolean getRightHeld()
	{
		return this.rightHeld;
	}
	
	public boolean getLeftHeld()
	{
		return this.leftHeld;
	}
	
	
	
	public void setDrawHitboxes(boolean b) {
		this.drawDebug = b;
	}
	
	@Override
	public void tick(GameStateState gs) {
		super.tick(gs);
		double xchange = 0;
		Location lastLoc = this.getLocation().clone();
		if(getLeftHeld())
			xchange=-speed;
		if(getRightHeld())
			xchange=speed;
		hitbox.addX((xchange/(double)Game.TPS)* Game.getTimeRate() * this.getTimeRate());
		Location newLoc = this.getLocation().clone();
		checkChange(lastLoc, newLoc, xchange, 0, gs);
		//Fix again because player movement
		fixCollisions(gs);
	}

	public void getHpUp() {
		if (healthUpgrades < 3) {
			healthUpgrades++;
			double temp = maxHealth;
			maxHealth = maxHealth * 1.25;
			health = health + (maxHealth - temp);
		}
	}
	
	public void attack() {
		
	}

	public void getSpUp() {
		if (speedUpgrades < 3) {
			speedUpgrades++;
			speed *= 1.25;
		}
	}

	public void getJumpUp() {
		if (jumpUpgrades < 3) {
			jumpUpgrades++;
			jumpHeight *= 1.25;
		}
	}
	
	public void getDmgUp() {
			damage = (int)(damage * 1.25);
	}

	public void handleUpgrade(Upgrade touched){
		if(touched.type() == 'H'){
			getHpUp();
		}
		else if(touched.type() == 'J'){
			getJumpUp();
		}
		else if(touched.type() == 'S'){
			getSpUp();
		}
		else if(touched.type() == 'D'){
			getDmgUp();
		}
	}

	public void getUseable(Useable u) {
		use = u;
	}

	public boolean drawHitboxEnabled() {
		return this.drawDebug;
	}
	
	@Override
	public void render(Graphics g, int xoff, int yoff, float scale) {
		super.render(g, xoff, yoff, scale);
		Color last = g.getColor();
		g.setColor(Color.WHITE);
		g.drawImage(ImageLoader.getImage("char"), xoff, yoff, (int)(hitbox.getBounds().getWidth() * scale),(int) (hitbox.getBounds().getHeight() * scale), null);
		drawHealthBar(g,xoff,yoff,scale);
		
		//	g.fillRect(xoff, yoff, (int)hitbox.getBounds().getWidth(),(int) hitbox.getBounds().getHeight());
		
		if(this.drawDebug)
			drawHitBoxes(g,xoff,yoff, scale);
	}


	@Override
	public Location getCenterLocation() {
		return getHitbox().getCenterLocation();
	}


	

}
