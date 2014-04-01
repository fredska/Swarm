package com.fska.swarm.map;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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
	
	private TiledMap tiledMap;
	
	public static MapData getInstance(){
		if(mapDataInstance == null){
			mapDataInstance = new MapData();
			mapDataInstance.resources = new HashSet<Resource>();
			mapDataInstance.buildings = new HashSet<Building>();
		}
		return mapDataInstance;
	}
	
	public MapData(){
		Texture.setEnforcePotImages(false);
		tiledMap = new TiledMap();
		TmxMapLoader loader = new TmxMapLoader();
		tiledMap = loader.load("tmx/Test_Map_2_Layer.tmx");
	}
	
	public TiledMap getTiledMap(){
		return this.tiledMap;
	}
	
	public void draw(Batch batch){
	}
	
	public Set<Resource> getResources(){
		return this.resources;
	}
	
	public Set<Building> getBuildings(){
		return this.buildings;
	}
}
