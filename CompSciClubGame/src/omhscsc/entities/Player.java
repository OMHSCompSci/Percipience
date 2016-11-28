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
	private boolean rightHeld, leftHeld, attacking;
	private int healthUpgrades, speedUpgrades, jumpUpgrades;
	private Useable use;

	public Player(String n, Hitbox h) {
		super(h);
		name = n;
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
		attacking = false;
	}
	
	
	public void attack(){
		attacking = true;
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
	
	public void takeDmg(double dmg) {
		health -= dmg;
	}

	public double getCurrentHp() {
		return health;
	}

	public double getMaxHp() {
		return maxHealth;
	}
	
	
	
	@Override
	public void tick(GameStateState gs) {
		super.tick(gs);
		if (health <= 0.0) {
			gs.gameOver();
		}
		double xchange = 0;
		if(getLeftHeld())
			xchange=-speed;
		if(getRightHeld())
			xchange=speed;
		hitbox.addX(xchange/(double)Game.TPS);
		//Fix again because player movement
		fixCollisions(gs);
		//Might remove this later
		if(hitbox.getLocation().getY() > 5000)
			this.takeDmg(this.maxHealth);
		//Changing attack style later
		//Test
	}

	public void getHpUp() {
		if (healthUpgrades < 3) {
			healthUpgrades++;
			double temp = maxHealth;
			maxHealth = maxHealth * 1.25;
			health = health + (maxHealth - temp);
		}
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

	@Override
	public void render(Graphics g, int xoff, int yoff) {
		super.render(g, xoff, yoff);
		Color last = g.getColor();
		g.setColor(Color.WHITE);
		g.drawImage(ImageLoader.getImage("char.png"), xoff, yoff, (int)hitbox.getBounds().getWidth(),(int) hitbox.getBounds().getHeight(), null);
		g.setColor(new Color(125, 38, 142));
		g.drawRect(xoff, yoff-15, 100, 10);
		g.setColor(Color.ORANGE);
		g.fillRect(xoff+1, yoff-14, (int)(100 * (this.getCurrentHp() / this.getMaxHp())), 9);
		
		//	g.fillRect(xoff, yoff, (int)hitbox.getBounds().getWidth(),(int) hitbox.getBounds().getHeight());
		
		drawHitBoxes(g,xoff,yoff);
	}


	

}
