package omhscsc.event;

import omhscsc.entities.Entity;
import omhscsc.util.Location;

/**
 * This event indicates an entity has moved from Location A to Location B. This is currently unimplemented.
 * @author Zach
 *
 */
public class EntityMoveEvent extends GameEvent {

	private final Entity entity;
	private final Location start, end;
	
	/**
	 * EntityMoveEvent is an event that is created when an Entity has moved from Point A to Point B.
	 * @param entity The entity which created this event.
	 * @param a The starting location.
	 * @param b The ending location.
	 */
	public EntityMoveEvent(Entity entity, Location a, Location b) {
		super(System.nanoTime());
		this.entity = entity;
		this.start = a;
		this.end = b;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	public Location getIntialLocation() {
		return this.start;
	}
	
	public Location getDestinationLocation() {
		return this.end;
	}
	
	public boolean cancel() {
		if(this.isCancelled())
			return true;
		if(System.nanoTime() - this.getTimeOfOccurance() < GameEvent.DEFAULT_TIMEOUT) {
			entity.setLocation(start);
			this.setCancelled(true);
			return true;
		}
		return false;
	}

}
