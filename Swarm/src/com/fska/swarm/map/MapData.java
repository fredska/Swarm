package com.fska.swarm.map;

import java.util.HashSet;
import java.util.Set;

import com.fska.swarm.entity.Building;
import com.fska.swarm.entity.Resource;


/**
 * This class contains all the locations of various Resources.  
 * 
 * Down the road, it will also control the loading & saving of different levels
 * @author fskallos
 *
 */
public class MapData{
	private static MapData mapDataInstance;
	private Set<Resource> resources;
	private Set<Building> buildings;
	
	public static MapData getInstance(){
		if(mapDataInstance == null){
			mapDataInstance = new MapData();
			mapDataInstance.resources = new HashSet<Resource>();
			mapDataInstance.buildings = new HashSet<Building>();
		}
		return mapDataInstance;
	}
	
	public Set<Resource> getResources(){
		return this.resources;
	}
	
	public Set<Building> getBuildings(){
		return this.buildings;
	}
}