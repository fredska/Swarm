package com.fska.swarm.entity;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.fska.swarm.entity.unit.Gatherer;

public abstract class Resource extends Entity {

	public static enum ResourceType{
		Stone, Food, Wood, Water
	};
	
	private int maxConcurrentGatherers;
	private Set<Gatherer> assignedGatherers;
	private float timeToHarvest; 
	private ResourceType resourceType;
	public Resource(Vector2 position, int maxConcurrentGatherers, float timeToHarvest,
				ResourceType resourceType){
		super(position);
		//The minimum number of gatherers at a resource is 1
		if(maxConcurrentGatherers >= 1)
			this.maxConcurrentGatherers = maxConcurrentGatherers;
		else
			this.maxConcurrentGatherers = 1;
		assignedGatherers = new HashSet<Gatherer>();
		this.timeToHarvest = timeToHarvest;
		this.resourceType = resourceType;
	}
	
	public boolean canGatherResource(Gatherer gatherer){
		if(assignedGatherers.size() < maxConcurrentGatherers){
			assignedGatherers.add(gatherer);
			return true;
		}
		return false;
	}
	public void assignToResource(Gatherer gatherer){
		assignedGatherers.add(gatherer);
	}
	public boolean isGathererAssigned(Gatherer gatherer){
		return assignedGatherers.contains(gatherer);
	}
	
	public boolean isResourceMaxed(){
		return assignedGatherers.size() == maxConcurrentGatherers;
	}
	
	public boolean harvest(float delta){
		if(timeToHarvest <= 0f) return false;
		switch(assignedGatherers.size()){
		case 0:
			//how can one harvest without workers?  This case should never be hit
			return false;
		case 1:
			timeToHarvest -= delta;
			break;
		default:
			timeToHarvest -= delta * ((1f / assignedGatherers.size()) + 0.15f);
		}
		return true;
	}
	
	public ResourceType getType(){
		return this.resourceType;
	}
	
	public abstract void draw(Batch batch);
	public abstract void update();

}
