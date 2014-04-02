package com.fska.swarm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.fska.swarm.map.MapData;

public class TmxLevelScreen implements Screen {

	private static final boolean DEBUG_MODE = true;
	private final ShapeRenderer debugRenderer = new ShapeRenderer();
	private IsometricStaggeredTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private static final int TILE_WIDTH = 64;
	private static final int TILE_HEIGHT = 32;
	private float timer = 0f;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		moveCamera(delta);
		camera.update();

		mapRenderer.setView(camera);
		mapRenderer.render();
		if (!Gdx.input.isButtonPressed(Buttons.MIDDLE) && timer >= 0.01f) {
			Vector2 point = new Vector2(Gdx.input.getX(),
					Gdx.graphics.getHeight() - Gdx.input.getY());
			// System.out.println("Camera: (" +
			// MathUtils.floor(camera.position.x)
			// + "," + MathUtils.floor(camera.position.y) + ")");
			// System.out.println("Camera: (" +
			// MathUtils.floor(camera.position.x)
			// + "," + MathUtils.floor(camera.position.y) + ")");
			Ray camRay = camera.getPickRay(Gdx.input.getX(), Gdx.input.getY());
			// getWorldToTilePos(camRay.origin.x, camRay.origin.y);
			getWorldToTilePos(camRay.origin.x, camRay.origin.y);
			timer = 0;
		}
		timer += delta;
	}

	private void getWorldToTilePos(float x, float y) {

		int tileX = MathUtils.floor(((x) + TILE_WIDTH / 2)  / TILE_WIDTH) - 1;
		int tileY = MathUtils.floor((2*(y) + TILE_HEIGHT / 2) / TILE_HEIGHT);
		System.out.println(tileX + "," + tileY);
		//In Debug Mode, draws a red dot where the cursor is on the screen.
		// A blue rectangle box should indicate which "tile" the cursor is 
		// hovering over
		if (DEBUG_MODE) {
			debugRenderer.setProjectionMatrix(camera.combined);
			debugRenderer.begin(ShapeType.Filled);
			debugRenderer.setColor(Color.RED);
			debugRenderer.circle(x, y, 5);
			debugRenderer.end();

			debugRenderer.begin(ShapeType.Line);
			for(int debugX = 0; debugX < 20; debugX++){
				for (int debugY = 0; debugY < 20; debugY++) {
					debugRenderer.setColor(Color.BLUE);
					debugRenderer.rect(debugX * TILE_WIDTH, debugY
							* TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);

					debugRenderer.setColor(Color.ORANGE);
					debugRenderer.rect(debugX * TILE_WIDTH - (TILE_WIDTH / 2f), debugY
							* TILE_HEIGHT + (TILE_HEIGHT / 2f), TILE_WIDTH, TILE_HEIGHT);
				}
			}
			debugRenderer.end();
			
			TiledMap map = MapData.getInstance().getTiledMap();
			TiledMapTile debugTile = map.getTileSets().getTile(176);
			TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
			if(tileX >= 0 && tileY >= 0 && tileX < layer.getWidth() && tileY < layer.getHeight()){
				Cell test = layer.getCell(tileX, tileY);
				test.setTile(debugTile);
			}
		}
	}

	private void moveCamera(float delta) {
		float modifier = 60 * delta;
		if (Gdx.input.isButtonPressed(Buttons.MIDDLE)) {

			camera.translate(Gdx.input.getDeltaX() * -modifier,
					Gdx.input.getDeltaY() * modifier);
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		mapRenderer = new IsometricStaggeredTiledMapRenderer(MapData
				.getInstance().getTiledMap(), 1 / 1f);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		 camera.translate(8, 0);
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
