package com.fska.swarm;

import com.badlogic.gdx.Game;
import com.fska.swarm.screens.ScreenManager;
import com.fska.swarm.screens.SwarmScreen;

public class Swarm extends Game {
	@Override
	public void create() {
		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().show(SwarmScreen.TMX_LEVEL);
	}

	@Override
	public void dispose() {
		super.dispose();
		ScreenManager.getInstance().dispose();
	}
}