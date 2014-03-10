package com.fska.swarm.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.IntMap;

public final class ScreenManager {
	private static ScreenManager instance;
	private Game game;
	private IntMap<Screen> screens;
	
	public ScreenManager(){
		screens = new IntMap<Screen>();
	}
	
	public static ScreenManager getInstance(){
		if(instance == null){
			instance = new ScreenManager();
		}
		return instance;
	}
	
	public void initialize(Game game){
		this.game = game;
	}
	
	public void show(SwarmScreen screen){
		if(this.game == null) return;
		if(!screens.containsKey(screen.ordinal())){
			screens.put(screen.ordinal(), screen.getScreenInstance());
		}
		game.setScreen(screens.get(screen.ordinal()));
	}
	
	public void dispose(SwarmScreen screen){
		if(!screens.containsKey(screen.ordinal())) return;
		screens.remove(screen.ordinal()).dispose();
	}
	
	public void dispose(){
		for(Screen screen : screens.values()){
			screen.dispose();
		}
		screens.clear();
		instance = null;
	}
}
