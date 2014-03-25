package com.fska.swarm.entity.resource;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.fska.swarm.entity.Resource;

public class Stone extends Resource {

	public Stone(Vector2 position) {
		super(position, 3, 5,Resource.ResourceType.Stone);
	}

	@Override
	public void draw(Batch batch) {
		
	}

	@Override
	public void update() {
		
	}

}
