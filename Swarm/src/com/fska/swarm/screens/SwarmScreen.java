package com.fska.swarm.screens;

import com.badlogic.gdx.Screen;

public enum SwarmScreen {
	MENU {
		@Override
		protected Screen getScreenInstance(){
			return new MenuScreen();
		}
	},
	GAME {
		@Override
		protected Screen getScreenInstance(){
			return new GameScreen();
		}
	},
	TMX_LEVEL {
		@Override
		protected Screen getScreenInstance(){
			return new TmxLevelScreen();
		}
	};
	
	protected abstract Screen getScreenInstance();
}
