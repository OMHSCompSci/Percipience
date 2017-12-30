package omhscsc.world.designer;

import java.awt.Color;
import java.awt.image.BufferedImage;

import omhscsc.util.Location;
import omhscsc.world.BackgroundLayer;
import omhscsc.world.World;

public class WorldDraft extends World {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2219813017675940148L;

	public WorldDraft(String worldName) {
		super(-1, new BackgroundLayer[0], new Location[0], Color.BLACK, null, worldName);
	}
	
	public void addSpawnpoint(Location spawnPoint) {
		Location[] newSpawnArray = new Location[this.possibleSpawnPoints.length+1];
		for(int i = 0; i < this.possibleSpawnPoints.length; i++) {
			newSpawnArray[i] = this.possibleSpawnPoints[i];
		}
		newSpawnArray[newSpawnArray.length-1] = spawnPoint;
		this.possibleSpawnPoints = newSpawnArray;
	}
	
	public void setBackgroundImage(BufferedImage bg) {
		this.backgroundImage = bg;
	}
	
	/**
	 * This will change the background id to whichever is next in the sequence
	 * @param bl
	 */
	public void appendBackgroundLayer(BackgroundLayer bl) {
		BackgroundLayer[] newBGLArray = new BackgroundLayer[this.backgroundLayers.length+1];
		for(int i = 0; i < this.possibleSpawnPoints.length; i++) {
			newBGLArray[i] = this.backgroundLayers[i];
		}
		newBGLArray[newBGLArray.length-1] = bl;
		bl.setLayerNumber(newBGLArray.length-1);
		this.backgroundLayers = newBGLArray;
	}

	/**
	 * Changes the layer id of the provided layer
	 * @param id
	 * @param bl
	 */
	public void setBackgroundLayer(int id, BackgroundLayer bl) {
		this.backgroundLayers[id] = bl;
		bl.setLayerNumber(id);
	}
	
	public void swapLayers(int layer1, int layer2) {
		BackgroundLayer l1 = this.backgroundLayers[layer1];
		BackgroundLayer l2 = this.backgroundLayers[layer2];
		l1.setLayerNumber(layer2);
		l2.setLayerNumber(layer1);
		this.backgroundLayers[layer1] = l2;
		this.backgroundLayers[layer2] = l1;
	}
	
	/**
	 * Changes background layer id for all layers affected
	 * @param position
	 * @param bl
	 */
	public void insertBackgroundLayer(int position, BackgroundLayer bl) {
		BackgroundLayer[] newArr = new BackgroundLayer[this.backgroundLayers.length+1];
		for(int i = 0; i < position; i++) {
			newArr[i] = this.backgroundLayers[i];
		}
		newArr[position] = bl;
		bl.setLayerNumber(position);
		for(int i = position+1; i < newArr.length; i++) {
			newArr[i] = this.backgroundLayers[i-1];
			newArr[i].setLayerNumber(i);
		}
		this.backgroundLayers = newArr;
	}
}
