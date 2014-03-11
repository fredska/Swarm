package com.fska.swarm.entity;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.fska.swarm.entity.unit.Gatherer;

public abstract class Resource extends Entity {

	private int maxConcurrentGatherers;
	private Set<Gatherer> assignedGatherers;
	public Resource(Vector2 position, int maxConcurrentGatherers){
		super(position);
		//The minimum number of gatherers at a resource is 1
		if(maxConcurrentGatherers >= 1)
			this.maxConcurrentGatherers = maxConcurrentGatherers;
		else
			this.maxConcurrentGatherers = 1;
		assignedGatherers = new HashSet<Gatherer>();
	}
	
	public boolean canGatherResource(Gatherer gatherer){
		if(assignedGatherers.size() < maxConcurrentGatherers){
			assignedGatherers.add(gatherer);
			return true;
		}
		return false;
	}
	
	public boolean isGathererAssigned(Gatherer gatherer){
		return assignedGatherers.contains(gatherer);
	}
	
	public abstract void draw(Batch batch);
	public abstract void update();

}
