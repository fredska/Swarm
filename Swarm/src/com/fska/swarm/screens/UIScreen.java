package com.fska.swarm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class UIScreen implements Screen {

	private Stage resourceStage;
	@Override
	public void render(float delta) {
		resourceStage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		resourceStage = new Stage(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		Table resourceTable = new Table(skin);
		resourceTable.add("--- Resources ---");
		resourceTable.row();
		resourceTable.add("Wood ");
		resourceTable.add("250");
		Table.drawDebug(resourceStage);
		resourceStage.addActor(resourceTable);
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
