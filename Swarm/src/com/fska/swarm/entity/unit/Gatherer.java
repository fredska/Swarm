package com.fska.swarm.entity.unit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fska.swarm.entity.Building;
import com.fska.swarm.entity.Resource;
import com.fska.swarm.entity.Unit;
import com.fska.swarm.entity.building.TownHall;

public class Gatherer extends Unit {

	enum Status{
		MOVING,
		HARVESTING,
		HAULING,
		WAITING,
		SLEEPING
	}
	private Texture texture;
	private TextureRegion textureRegion;
	private Vector2 target;
	private Status status;
	
	public Gatherer(Vector2 position, float maxHealth, float speed) {
		super(position, maxHealth, speed);
		texture = new Texture(Gdx.files.internal("units/Gatherer_1.png"));
		textureRegion = new TextureRegion(texture);
		status = Status.WAITING;
	}

	@Override
	public void update() {
		if(hasReachedTarget()){
			//If target reached is a Resource, set status to Gathering
			//If target is a resource drop-off, set status to Waiting
			//If target is a bed, set status to Sleeping
			
			//For now, only actions are lazily walking around
			Vector2 currentPosition = super.getPosition();
			float x = currentPosition.x + MathUtils.random(-10, 10);
			float y = currentPosition.y + MathUtils.random(-10, 10);
			x = (x <= Gdx.graphics.getWidth())? x : Gdx.graphics.getWidth();
			x = (x > 0)? x : 0;
			y = (y <= Gdx.graphics.getHeight())? y : Gdx.graphics.getHeight();
			y = (y > 0)? y : 0;
			Vector2 newTarget = new Vector2(x,y);
			this.target = newTarget;
		}
		textureRegion.setRegion(
				super.getPosition().x, 
				super.getPosition().y,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		move(Gdx.graphics.getDeltaTime(), this.target);
	}
	
	private boolean hasReachedTarget(){
		if(target == null) return true;
		return target.dst2(super.getPosition()) < 11;
	}

	@Override
	public void draw(Batch batch) {
		//This is presuming the batch.begin() has been called... otherwise lots of crashing!
		batch.draw(texture, super.getPosition().x, super.getPosition().y);
	}

}
