package com.fska.swarm.screens;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.fska.swarm.entity.Building;
import com.fska.swarm.entity.Entity;
import com.fska.swarm.entity.Resource.ResourceType;
import com.fska.swarm.entity.building.TownHall;
import com.fska.swarm.entity.resource.Tree;
import com.fska.swarm.entity.unit.Gatherer;
import com.fska.swarm.map.MapData;
import com.fska.swarm.player.Player;

public class GameScreen implements Screen {

	private Set<Gatherer> gatherers;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private SortedSet<Entity> entities;
	

	
	private UIScreen uiScreen;

	@Override
	public void render(float delta) {
		// Clear out the screen with a black background
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		moveCamera(delta);
		entities.clear();
		entities.addAll(MapData.getInstance().getBuildings());
		entities.addAll(MapData.getInstance().getResources());
		entities.addAll(gatherers);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Entity entity : entities) {
			entity.update();
			entity.draw(batch);
		}
		batch.end();
		
		
		uiScreen.render(delta);
	}
	
	private void moveCamera(float delta){
		float cameraSpeed = 100 * delta;
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			camera.translate(-cameraSpeed, 0);
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			camera.translate(cameraSpeed, 0);
		}
		if(Gdx.input.isKeyPressed(Keys.UP)){
			camera.translate(0, cameraSpeed);
		}if(Gdx.input.isKeyPressed(Keys.DOWN)){
			camera.translate(0, -cameraSpeed);
		}
		if(Gdx.input.isButtonPressed(Buttons.MIDDLE)){
			int getX = Gdx.input.getDeltaX();
			int getY = Gdx.input.getDeltaY();
			camera.translate(cameraSpeed * -getX, cameraSpeed * getY);
		}
		camera.update();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		final int width = Gdx.graphics.getWidth();
		final int height = Gdx.graphics.getHeight();
		
		//Create a town hall or two :)
		Set<Building> initialBuildings = MapData.getInstance().getBuildings();
		TownHall player1TownHall = new TownHall(new Vector2(width * 0.9f, height * 0.8f));
		TownHall player2TownHall = new TownHall(new Vector2(width * 0.1f, height * 0.2f));
		initialBuildings.add(player1TownHall);
		initialBuildings.add(player2TownHall);
		
		gatherers = new HashSet<Gatherer>();
		final float gathererSpeed = 75;
		for (int i = 0; i < 20; i++) {
			if(MathUtils.randomBoolean()){
				gatherers.add(new Gatherer(player1TownHall.getCenter(), 100, gathererSpeed, player1TownHall));
			}
			else {
				gatherers.add(new Gatherer(player2TownHall.getCenter(), 100, gathererSpeed, player2TownHall));
			}
		}
		batch = new SpriteBatch();
		batch.enableBlending();
		camera = new OrthographicCamera(width, height);
		camera.zoom = 1f;
		camera.position.set(width  / 2f, height / 2f, 0);

		// Initialize the mapData object and add some trees :)
		MapData mapData = MapData.getInstance();
		for (int i = 0; i < 150; i++) {
			Tree woodTree = new Tree(
					new Vector2(MathUtils.random(width*camera.zoom) - width * (camera.zoom / 4f)
							, MathUtils.random(height*camera.zoom) - height * (camera.zoom / 4f)));
			mapData.getResources().add(woodTree);
		}

		entities = new TreeSet<Entity>(new Comparator<Entity>() {
			@Override
			public int compare(Entity o1, Entity o2) {
				if ((o1.getPosition().y + o1.getHeight()) >= (o2.getPosition().y + o2
						.getHeight()))
					return -1;
				else if ((o1.getPosition().y + o1.getHeight()) < (o2
						.getPosition().y + o2.getHeight()))
					return 1;
				return 0;
			}
		});
		
		//Create a new player & fill out some data on them
		Player player1 = new Player(1, Color.BLUE);
		
		
		uiScreen = new UIScreen();
		uiScreen.show();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
