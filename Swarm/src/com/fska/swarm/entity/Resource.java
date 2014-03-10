package com.fska.swarm.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public abstract class Resource extends Entity {

	public Resource(Vector2 position){
		super(position);
	}
	
	public abstract void draw(Batch batch);
	public abstract void update();

}
