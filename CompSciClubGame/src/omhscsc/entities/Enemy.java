package omhscsc.entities;

import java.awt.Color;
import java.awt.Graphics;

import omhscsc.GameObject;
import omhscsc.graphic.Renderable;
import omhscsc.items.Upgrade;
import omhscsc.items.Useable;
import omhscsc.state.GameStateState;
import omhscsc.util.Hitbox;
import omhscsc.util.ImageLoader;

public abstract class Enemy extends Entity implements Renderable {
	private double hp, maxHp;
	private boolean isBoss;
	private double tickCounter;
	private Hitbox hit;
	private boolean dead;
	
	public Enemy(double mH, boolean b, Hitbox h){
		maxHp = mH;
		hp = maxHp;
		dead = false;
		isBoss = b;
		hit = h;
	}
	public void takeDmg(double dmg) {
		hp-=dmg;
	}
	
	public double getCurrentHp(){
		return hp;
	}
	public double getMaxHp(){
		return maxHp;
	}
	public Hitbox getHitbox(){
		return hit;
	}

}
