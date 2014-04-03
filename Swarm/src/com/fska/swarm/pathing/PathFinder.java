package com.fska.swarm.pathing;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.fska.swarm.map.MapData;

public abstract class PathFinder {
	private Set<Integer> validTiles;
	public PathFinder(){
		
		validTiles = new HashSet<Integer>();
		for(TiledMapTile tile : MapData.getInstance().getTiledMap().getTileSets().getTileSet(0)){
			validTiles.add(tile.getId());
		}
	}
	public abstract void findPath(TiledMap map, TiledMapTile start, TiledMapTile dest);
}
