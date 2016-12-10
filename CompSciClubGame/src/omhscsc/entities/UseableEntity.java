package omhscsc.entities;

import omhscsc.GameObject;
import omhscsc.items.Upgrade;
import omhscsc.items.Useable;
import omhscsc.state.GameStateState;
import omhscsc.util.Location;
import omhscsc.util.geometry.Hitbox;

//Might replace with item entity
public class UseableEntity extends GameObject {
	private Useable use;
	private Hitbox hit;
	public UseableEntity(Useable u, Hitbox h){
		use = u;
		hit = h;
	}
	public void tick(GameStateState gs) {
		Player p = gs.getPlayer();
		if(p.getHitbox().getBounds().intersects(hit.getBounds())){
			p.getUseable(use);
			gs.removeGameObject(this);
		}

	}
	public Useable getUseable(){
		return use;
	}
}
