package com.fska.swarm.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public abstract class Building extends Entity {

	public Building(Vector2 position){
		super(position);
	}
	
	public abstract void draw(Batch batch);
	public abstract void update();
}
