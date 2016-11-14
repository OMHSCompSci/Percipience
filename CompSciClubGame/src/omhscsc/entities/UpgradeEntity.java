package omhscsc.entities;

import omhscsc.GameObject;
import omhscsc.items.Upgrade;
import omhscsc.items.Useable;
import omhscsc.state.GameStateState;
import omhscsc.util.Hitbox;

public class UpgradeEntity extends GameObject {
	private Upgrade up;
	private Hitbox hit;
	public UpgradeEntity(Upgrade u, Hitbox h){
		up = u;
		hit = h;
	}
	public void tick(GameStateState gs) {
		Player p = gs.getPlayer();
		if(p.getHitbox().getBounds().intersects(hit.getBounds())){
			p.handleUpgrade(up);
			gs.removeGameObject(this);
		}

	}
	public Upgrade getUpgrade(){
		return up;
	}
}
