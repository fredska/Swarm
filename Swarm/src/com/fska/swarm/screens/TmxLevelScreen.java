package com.fska.swarm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.fska.swarm.map.MapData;

public class TmxLevelScreen implements Screen {

	private IsometricTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		moveCamera(delta);
		camera.update();
		
		mapRenderer.setView(camera);
		mapRenderer.render();
	}
	
	private void moveCamera(float delta){
		float modifier = 50 * delta;
		if(Gdx.input.isButtonPressed(Buttons.MIDDLE)){
			
			camera.translate(Gdx.input.getDeltaX() * - modifier, Gdx.input.getDeltaY() * modifier);
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		mapRenderer = 
				new IsometricTiledMapRenderer(
						MapData.getInstance().getTiledMap(), 1/1f);
		camera = new OrthographicCamera(
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.translate(2500, 1200);
		camera.update();
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
