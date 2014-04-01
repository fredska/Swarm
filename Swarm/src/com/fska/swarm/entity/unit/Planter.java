package com.fska.swarm.entity.unit;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.fska.swarm.entity.Unit;

public class Planter extends Unit {

	public Planter(Vector2 position){
		this(position, 100, 50);
	}
	
	public Planter(Vector2 position, float maxHealth, float speed) {
		super(position, maxHealth, speed);
	}

	@Override
	public void draw(Batch batch) {
	}

	@Override
	public void update() {
	}

}
