package com.fska.swarm.entity.unit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fska.swarm.common.Common;
import com.fska.swarm.entity.Building;
import com.fska.swarm.entity.Resource;
import com.fska.swarm.entity.Unit;
import com.fska.swarm.entity.building.TownHall;
import com.fska.swarm.map.MapData;

public class Gatherer extends Unit {

	enum Status {
		MOVING, HARVESTING, HAULING, WAITING, SLEEPING
	}

	private Texture texture;
	private TextureRegion textureRegion;
	private Vector2 target;
	private Status status;
	private float timeToWait = 0f;
	private float walkingDuration = 0f;
	private TownHall myTownHall;
	private float maxLazyDistanceFromTownHall;
	private Animation walkAnimation;
	private Resource targetResource;
	private Resource hauledResource;

	public Gatherer(Vector2 position, float maxHealth, float speed) {
		super(position, maxHealth, speed);
		texture = new Texture(Gdx.files.internal("units/Gatherer_1.png"));
		TextureRegion[][] frames = TextureRegion.split(texture, 32, 32);
		textureRegion = frames[0][0];
		walkAnimation = new Animation(0.1f, frames[0][0], frames[0][1],
				frames[0][2]);
		walkAnimation.setPlayMode(Animation.LOOP_PINGPONG);
		System.out.println(frames.length + " :: " + frames[0].length);
		status = Status.WAITING;
		myTownHall = new TownHall(new Vector2(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2));
		maxLazyDistanceFromTownHall = 100f;
	}

	@Override
	public void update() {
		//If the gatherer has reached their target:
		//VALID TARGETS:
		// Resource, Haul drop off
		if (hasReachedTarget()) {
			// If target reached is a Resource, set status to Gathering
			// If target is a resource drop-off, set status to Waiting
			// If target is a bed, set status to Sleeping
			switch (status) {
			case HAULING:
				// Remove the resource and add it to the stockpile
				hauledResource = null;
				break;
			default:
			}

			if (hauledResource != null) {
				if (myTownHall == null) {
					this.target = new Vector2(0, 0);
				} else
					this.target = myTownHall.getCenter();
				status = Status.HAULING;
			}
			if (targetResource != null) {
				this.target = targetResource.getCenter();
			}
			if (targetResource == null) {
				targetResource = findNearestResource();
			}

			
			// For now, only actions are lazily walking around
			if (this.target == null) {
				this.target = findRandomSpot();
			}
			
			status = Status.WAITING;
			timeToWait = MathUtils.random(0.25f) + 0.25f;
		}
		if (timeToWait > 0f) {
			if(status == Status.WAITING){
				textureRegion = walkAnimation.getKeyFrame(0.15f);
			}
			if(status == Status.HARVESTING){
				//Harvesting Animation goes here
			}
			walkingDuration = 0f;
			timeToWait -= Gdx.graphics.getDeltaTime();
			return;
		} else {
			move(Gdx.graphics.getDeltaTime(), this.target);
			walkingDuration += Gdx.graphics.getDeltaTime();
			textureRegion = walkAnimation.getKeyFrame(walkingDuration, true);
			status = Status.MOVING;
		}
	}

	public Vector2 findRandomSpot() {
		Vector2 currentPosition = super.getPosition();
		float x = currentPosition.x + MathUtils.random(-40, 40);
		float y = currentPosition.y + MathUtils.random(-40, 40);
		Gdx.graphics.setTitle("Swarm: Version " + Common.VERSION);
		x = (x <= Gdx.graphics.getWidth() - Common.TILE_WIDTH) ? x
				: Gdx.graphics.getWidth() - Common.TILE_WIDTH;
		x = (x > 0) ? x : 0;
		y = (y <= Gdx.graphics.getHeight() - Common.TILE_HEIGHT) ? y
				: Gdx.graphics.getHeight() - Common.TILE_HEIGHT;
		y = (y > 0) ? y : 0;
		return new Vector2(x, y);
	}
	
	public Resource findNearestResource(){
		// Lazily look through all the resources & find the nearest one
		// within eyesight
		Resource nearestResource = null;
		float distanceToCurrentResource = 0f;
		for (Resource resource : MapData.getInstance().getResources()) {
			if (resource.getCenter().dst2(getCenter()) < 4000000) {
				if (nearestResource == null) {
					nearestResource = resource;
					distanceToCurrentResource = nearestResource
							.getCenter().dst2(this.getCenter());
				} else {
					float distanceToNewResource = resource.getCenter()
							.dst2(this.getCenter());
					nearestResource = (distanceToNewResource < distanceToCurrentResource) ? resource
							: nearestResource;
				}
			}
		}
		return nearestResource;
	}

	private boolean hasReachedTarget() {
		if (target == null)
			return true;
		return target.dst2(super.getPosition()) < 1;
	}

	@Override
	public void draw(Batch batch) {
		// This is presuming the batch.begin() has been called... otherwise lots
		// of crashing!
		batch.draw(textureRegion, super.getPosition().x, super.getPosition().y);
	}

}
