package com.fska.swarm.screens;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fska.swarm.entity.Entity;
import com.fska.swarm.entity.resource.Tree;
import com.fska.swarm.entity.unit.Gatherer;
import com.fska.swarm.map.MapData;

public class GameScreen implements Screen {

	private Set<Gatherer> gatherers;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private SortedSet<Entity> entities;

	@Override
	public void render(float delta) {
		// Clear out the screen with a black background
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		entities.clear();
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

	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		final int width = Gdx.graphics.getWidth();
		final int height = Gdx.graphics.getHeight();
		Vector2 startPosition = new Vector2(MathUtils.random(width),
				MathUtils.random(height));
		gatherers = new HashSet<Gatherer>();
		for (int i = 0; i < 750; i++) {
			startPosition = new Vector2(MathUtils.random(width),
					MathUtils.random(height));
			gatherers.add(new Gatherer(startPosition, 100, 55));
		}
		batch = new SpriteBatch();
		batch.enableBlending();
		camera = new OrthographicCamera(width, height);
		camera.zoom = 1.75f;
		camera.position.set(width  / 2f, height / 2f, 0);

		// Initialize the mapData object and add some trees :)
		MapData mapData = MapData.getInstance();
		for (int i = 0; i < 1500; i++) {
			Tree woodTree = new Tree(
					new Vector2(MathUtils.random(width*1.5f) - width * 0.25f
							, MathUtils.random(height*1.5f) - height * 0.25f));
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
