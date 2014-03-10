package com.fska.swarm.screens;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fska.swarm.entity.unit.Gatherer;

public class GameScreen implements Screen {

	private Set<Gatherer> gatherers; 
	private ShapeRenderer renderer;
	private SpriteBatch batch;
	private Camera camera;
	@Override
	public void render(float delta) {
		//Clear out the screen with a black background
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(Gatherer gatherer : gatherers){
			gatherer.update();
			gatherer.draw(batch);
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
		for(int i = 0; i < 50; i++){
			startPosition = new Vector2(MathUtils.random(width), 
					MathUtils.random(height));
			gatherers.add(new Gatherer(startPosition, 100, 25));
		}
		batch = new SpriteBatch();
		batch.enableBlending();
		camera = new OrthographicCamera(width,height);
		camera.position.set(width/2, height/2, 0);
		
		renderer = new ShapeRenderer();
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
