package com.fska.swarm.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.fska.swarm.entity.Resource.ResourceType;
import com.fska.swarm.player.Player;

public class UIScreen implements Screen {

	/* UI Parameters */
	private Stage resourceStage;
	private Label woodCountLabel;
	
	@Override
	public void render(float delta) {
		woodCountLabel.setText(Integer.toString(Player.getPlayers().get(1).getResourceCount(ResourceType.Wood)));
		resourceStage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		setupUIStage();
	}
	
	private void setupUIStage(){
		resourceStage = new Stage(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),false);
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		Table resourceTable = new Table(skin);
		woodCountLabel = new Label(Integer.toString(Player.getPlayers().get(1).getResourceCount(ResourceType.Wood)),
				skin);
		resourceTable.add("--- Resources ---");
		resourceTable.row();
		resourceTable.add("Wood ");
		resourceTable.add(woodCountLabel);
		Table.drawDebug(resourceStage);
		resourceTable.setPosition(100, Gdx.graphics.getHeight()- 50);
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
