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

public class Player extends GameObject implements Anchor, Renderable {
	private double hp, maxHp, jumpHeight, speed, dmgMod;
	private String name;
	private boolean rightHeld, leftHeld, attacking;
	private Hitbox hitbox;
	private Velocity velocity;
	private int hpUpgrades, speedUpgrades, jumpUpgrades;
	private Useable use;
	private boolean canJump;

	public Player(String n, Location l) {
		name = n;
		canJump = true;
		hpUpgrades = 0;
		speedUpgrades = 0;
		jumpUpgrades = 0;
		hp = 100.0;
		maxHp = 100.0;
		speed = 220;
		dmgMod = 20;
		jumpHeight = 1;
		rightHeld = false;
		leftHeld = false;
		velocity = new Velocity(0,0);
		hitbox = new Hitbox(70, 70, l);
		use = null;
		attacking = false;
	}
	
	public boolean canJump()
	{
		return canJump;
	}
	public void attack(){
		attacking = true;
	}
	
	public void setCanJump(boolean jump) {
		this.canJump=jump;
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
		hp -= dmg;
	}

	public double getCurrentHp() {
		return hp;
	}

	public double getMaxHp() {
		return maxHp;
	}

	@Override
	public void tick(GameStateState gs) {
		if(this.getHitbox().getLocation().getX() >= 3300){
			gs.win();
		}
		if (hp <= 0.0) {
			gs.gameOver();
		}
		velocity.addY((double)Game.GRAVITY/(double)Game.TPS);
		hitbox.addY(velocity.getY()/(double)Game.TPS);
		hitbox.addX(velocity.getX()/(double)Game.TPS);
		velocity.addX((velocity.getX()*-.9)/(double)Game.TPS);
		if(velocity.getX() < 50)
			velocity.setX(0);
		double xchange = 0;
		if(getLeftHeld())
			xchange=-speed;
		if(getRightHeld())
			xchange=speed;
		hitbox.addX(xchange/(double)Game.TPS);
		for(WorldObject wo: gs.getCurrentWorld().getWorldObjects()){
			if(wo.getHitbox().getBounds().intersects(getLeftBound())){
				velocity.setX(0);
				hitbox.setX(wo.getHitbox().getLocation().getX()+wo.getHitbox().getBounds().getWidth());
			}
			if(wo.getHitbox().getBounds().intersects(getRightBound())){
				velocity.setX(0);
				hitbox.setX(wo.getHitbox().getLocation().getX()-hitbox.getBounds().getWidth());
			}
			if(wo.getHitbox().getBounds().intersects(getTopBound())){
				velocity.setY(0);
				hitbox.setY(wo.getHitbox().getLocation().getY()+wo.getHitbox().getBounds().getHeight());
			}
			if(wo.getHitbox().getBounds().intersects(getBottomBound())){
				velocity.setY(0);
				hitbox.setY(wo.getHitbox().getLocation().getY()-hitbox.getBounds().getHeight());
				setCanJump(true);
			}
		}
		if(hitbox.getLocation().getY() > 5000)
			this.takeDmg(this.maxHp);
		if(attacking){
			Hitbox newHit = new Hitbox(300, 300, new Location(this.getHitbox().getLocation().getX()-75, this.getHitbox().getLocation().getY()-75,this.getHitbox().getLocation().getWorld()));
			for(GameObject go: gs.getGameObjects()){
				if(go instanceof Enemy && newHit.getBounds().intersects(((Enemy) go).getHitbox().getBounds())){
					((Enemy) go).takeDmg(dmgMod);
				}
				
			}
			attacking = false;
		}
	}

	public void getHpUp() {
		if (hpUpgrades < 3) {
			hpUpgrades++;
			double temp = maxHp;
			maxHp = maxHp * 1.25;
			hp = hp + (maxHp - temp);
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
			dmgMod = (int)(dmgMod * 1.25);
	}
	public void jump()
	{
		if(!canJump)
			return;
		velocity.setY(-jumpHeight*400);
		hitbox.addY(-5);
		canJump = false;
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
		Color last = g.getColor();
		g.setColor(Color.WHITE);
		g.drawImage(ImageLoader.getImage("char.png"), xoff, yoff, (int)hitbox.getBounds().getWidth(),(int) hitbox.getBounds().getHeight(), null);
		g.setColor(new Color(125, 38, 142));
		g.drawRect(xoff, yoff-15, 100, 10);
		g.setColor(Color.ORANGE);
		g.fillRect(xoff+1, yoff-14, (int)(100 * (this.getCurrentHp() / this.getMaxHp())), 9);
		
		//	g.fillRect(xoff, yoff, (int)hitbox.getBounds().getWidth(),(int) hitbox.getBounds().getHeight());
		
	//	drawHitBoxes(g,xoff,yoff);
	}

	@Override
	public Hitbox getHitbox() {
		
		return hitbox;
	}

	@Override
	public Location getLocation() {
		return hitbox.getLocation();
	}
	
	public Rectangle getLeftBound() {
		return new Rectangle((int)hitbox.getLocation().getX(), (int)hitbox.getLocation().getY()+5, 10, (int)this.hitbox.getBounds().getHeight()-10);
	}
	
	public Rectangle getRightBound() {
		return new Rectangle((int)hitbox.getLocation().getX() + (int)(hitbox.getBounds().getWidth()-10), (int)hitbox.getLocation().getY()+5, 10, (int)this.hitbox.getBounds().getHeight()-10);
	}
	
	public Rectangle getTopBound() {
		return new Rectangle((int)hitbox.getLocation().getX()+5, (int)hitbox.getLocation().getY(), (int)(this.hitbox.getBounds().getWidth()-10), 10);
	}
	
	public Rectangle getBottomBound() {
		return new Rectangle((int)hitbox.getLocation().getX()+5, (int)hitbox.getLocation().getY()+(int)(hitbox.getBounds().getHeight()-10), (int)(this.hitbox.getBounds().getWidth()-10), 10);
	}
	
	public void drawHitBoxes(Graphics g, int x, int y)
	{
		g.setColor(Color.ORANGE);
		Graphics2D g2 = (Graphics2D)g;
		g2.draw(getLeftBound());
		g2.setColor(Color.RED);
		g2.draw(getRightBound());
		g2.setColor(Color.PINK);
		g2.draw(getTopBound());
		g2.setColor(Color.BLUE);
		g2.draw(getBottomBound());
	}
	
	

}
