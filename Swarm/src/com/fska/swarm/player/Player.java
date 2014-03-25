package com.fska.swarm.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.fska.swarm.entity.Building;
import com.fska.swarm.entity.Resource;
import com.fska.swarm.entity.Unit;
import com.fska.swarm.entity.Resource.ResourceType;
import com.fska.swarm.entity.resource.Food;

/**
 * Contains all information about the player.  This includes playerId,
 * preferred color (?), resource count, research(?), population count,
 * building count
 * @author fskallos
 *
 */
public class Player {

	private static Map<Integer, Player> players;
	private int playerId;
	private Color playerColor;
	private List<Unit> playerUnits;
	private List<Building> playerBuildings;
	private Map<Resource.ResourceType, Integer> playerResourceCount;
	
	public Player(int playerId, Color playerColor){
		this.playerId = playerId;
		getPlayers().put(playerId, this);
		this.playerColor = playerColor;
		playerUnits = new ArrayList<Unit>();
		playerBuildings = new ArrayList<Building>();
		playerResourceCount = new HashMap<ResourceType, Integer>();
		for(ResourceType resourceType : ResourceType.values()){
			playerResourceCount.put(resourceType, 0);
		}
		
		//Start with 10 wood by default
		playerResourceCount.put(ResourceType.Wood, 10);
	}
	
	public static Map<Integer, Player> getPlayers(){
		if(players == null){
			players = new HashMap<Integer, Player>();
		}
		return players;
	}
	
	public void addResource(ResourceType resourceType){
		int resourceCount = playerResourceCount.get(resourceType);
		playerResourceCount.put(resourceType , resourceCount + 1);
	}
	
	public int getResourceCount(ResourceType type){
		return playerResourceCount.get(type);
	}
}
