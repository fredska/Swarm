package com.fska.swarm.entity.unit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fska.swarm.common.Common;
import com.fska.swarm.entity.Resource;
import com.fska.swarm.entity.Unit;
import com.fska.swarm.entity.building.TownHall;
import com.fska.swarm.map.MapData;

/**
 * Basic Gathering Unit
 * Actions:  Move, Sleep, Harvest, Build, Haul, Eat
 * @author fskallos
 *
 */
public class Gatherer extends Unit {

	enum Status {
		MOVE, WAIT, SLEEP, WORKING
		}
	
	enum Action {
		HARVEST, HAUL, BUILD
	}

	private Texture texture;
	private TextureRegion textureRegion;
	private Vector2 destination;
	private Status currentStatus;
	private Action action;
	private float timeToWait = 0f;
	private float walkingDuration = 0f;
	private TownHall myTownHall;
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
		currentStatus = Status.WAIT;
		myTownHall = new TownHall(new Vector2(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2));
	}
	
	//This method provides basic AI.  Ideally this will be refactored into it's own class, but for now
	// there isn't much point in doing so.
	@Override
	public void update() {
		switch(currentStatus){
		case MOVE:
			if(!hasReachedTarget()){
				move(Gdx.graphics.getDeltaTime(), this.destination);
				walkingDuration += Gdx.graphics.getDeltaTime();
				textureRegion = walkAnimation.getKeyFrame(walkingDuration, true);
				return;
			} else {
				walkingDuration = 0f;
				currentStatus = Status.WORKING;
				if(action == Action.HARVEST){
					destination = null;
				}
				if(action == Action.HAUL){
					hauledResource = null;
					destination = null;
					currentStatus = Status.WAIT;
					timeToWait = 0.75f;
					action = null;
				}
			}
			break;
		case WORKING:
			if(action == Action.HARVEST){
				if(targetResource == null){
					currentStatus = Status.MOVE;
					destination = findRandomSpot();
					return;
				}
				if(!targetResource.harvest(Gdx.graphics.getDeltaTime())){
					if(MapData.getInstance().getResources().remove(targetResource)){
						this.destination = returnToNearestStockpile(targetResource);
						currentStatus = Status.MOVE;
						action = Action.HAUL;
					} else {
						action = null;
						currentStatus = Status.WAIT;
						timeToWait = 0.5f;
					}
					hauledResource = targetResource;
					targetResource = null;
				}
			}
			break;
		case SLEEP:
			currentStatus = Status.WAIT;
			break;
		case WAIT:
			if(timeToWait <= 0f){
				//Get a new action
				//Setup a priority system here, but for now just gather resources
				currentStatus = Status.MOVE;
				action = Action.HARVEST;
				targetResource = findNearestResource();
				if(targetResource != null){
					targetResource.assignToResource(this);
					this.destination = targetResource.getCenter();
				}
			} else {
				timeToWait -= Gdx.graphics.getDeltaTime();
				textureRegion = walkAnimation.getKeyFrame(0.15f);
			}
			break;
		//Default to Wait
		default:
			currentStatus = Status.WAIT;
		}
		/*
		//If the gatherer has reached their target:
		//VALID TARGETS:
		// Resource, Haul drop off
		if (hasReachedTarget()) {
			// If target reached is a Resource, set status to Gathering
			// If target is a resource drop-off, set status to Waiting
			// If target is a bed, set status to Sleeping
			switch (status) {
			case HARVEST:
				if(!targetResource.harvest(Gdx.graphics.getDeltaTime())){
					if(MapData.getInstance().getResources().remove(targetResource)){
						hauledResource = targetResource;
						targetResource = null;
						status = Status.HAUL;
						target = myTownHall.getCenter();
					} else {
						targetResource = null;
						status = Status.WAIT;
						timeToWait = MathUtils.random(0.5f) + 0.25f;
					}
				}
				break;
			case HAUL:
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
				status = Status.HAUL;
			}
			if (targetResource != null) {
				this.target = targetResource.getCenter();
				status = Status.HARVEST;
				target = null;
				return;
			}
			if (targetResource == null) {
				targetResource = findNearestResource();
				targetResource.canGatherResource(this);
			}

			
			// For now, only actions are lazily walking around
			if (this.target == null) {
				this.target = findRandomSpot();
			}
			
			status = Status.WAIT;
			timeToWait = MathUtils.random(0.25f) + 0.25f;
		}
		if (timeToWait > 0f) {
			if(status == Status.WAIT){
				textureRegion = walkAnimation.getKeyFrame(0.15f);
			}
			if(status == Status.HARVEST){
				//Harvesting Animation goes here
			}
			walkingDuration = 0f;
			timeToWait -= Gdx.graphics.getDeltaTime();
			return;
		} else {
			move(Gdx.graphics.getDeltaTime(), this.target);
			walkingDuration += Gdx.graphics.getDeltaTime();
			textureRegion = walkAnimation.getKeyFrame(walkingDuration, true);
			status = Status.MOVE;
		}
		*/
	}
	
	/**
	 * Returns the nearest drop off for the given resource type
	 * For now, this is just the town Hall, if one is available
	 * @return
	 */
	public Vector2 returnToNearestStockpile(Resource resource){
		if(myTownHall == null)
			return new Vector2(0,0);
		return myTownHall.getCenter();
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
			if(resource.isResourceMaxed()) continue;
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
		if (destination == null)
			return true;
		return destination.dst2(super.getPosition()) < 1;
	}

	@Override
	public void draw(Batch batch) {
		// This is presuming the batch.begin() has been called... otherwise lots
		// of crashing!
		batch.draw(textureRegion, super.getPosition().x, super.getPosition().y);
	}

}
