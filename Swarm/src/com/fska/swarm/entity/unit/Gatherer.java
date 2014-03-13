package com.fska.swarm.entity.unit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
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
		currentStatus = Status.WAIT;
		myTownHall = new TownHall(new Vector2(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2));
	}
	
	//Extend the above constructor, useful if I have a town hall to assign!
	public Gatherer(Vector2 position, float maxHealth, float speed, TownHall myTownHall) {
		this(position, maxHealth, speed);
		this.myTownHall = myTownHall;
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
						hauledResource = targetResource;
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
