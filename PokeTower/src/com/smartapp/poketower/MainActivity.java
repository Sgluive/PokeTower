package com.smartapp.poketower;

import java.io.IOException;

import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.Menu;

import map.Map;

import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;

import display.TowerDisplay;

import spawner.Spawner;

public class MainActivity extends SimpleBaseGameActivity {

	private Map _map = null;
	private TowerDisplay _towerDisplay = null;
	private Spawner _spawner = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		_towerDisplay = new TowerDisplay();
		_spawner = new Spawner();
		_spawner.loadSpawner(this.getResources().openRawResource(R.raw.spawn1));
		try {
			this._map = new Map();
			this._map.loadMap(this.getResources().openRawResource(R.raw.map1));
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.activity_main, menu);
//		return true;
//	}

	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, this._map.getWidth(), this._map.getHeight());
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
				new RatioResolutionPolicy(this._map.getWidth(), this._map.getHeight()), camera);
	}

	@Override
	protected void onCreateResources() {
		this._map.loadRessources(this);
		this._towerDisplay.loadRessources(this);
	}

	@Override
	protected Scene onCreateScene() {
		final Scene scene = new Scene();

		this._map.displayMap(this, scene);
		//Towers
		this._towerDisplay.addTower(this, scene, "Bulbasaur", 0, 0);
		this._towerDisplay.addTower(this, scene, "Ivysaur", 1, 0);
		this._towerDisplay.addTower(this, scene, "Venusaur", 2, 0);
		this._towerDisplay.addTower(this, scene, "Charmander", 3, 0);
		this._towerDisplay.addTower(this, scene, "Charmeleon", 4, 0);
		this._towerDisplay.addTower(this, scene, "Charizard", 5, 0);
		this._towerDisplay.addTower(this, scene, "Squirtle", 6, 0);
		this._towerDisplay.addTower(this, scene, "Wartortle", 7, 0);
		this._towerDisplay.addTower(this, scene, "Blastoise", 8, 0);
		this._towerDisplay.addTower(this, scene, "Magnemite", 9, 0);
		this._towerDisplay.addTower(this, scene, "Magneton", 10, 0);
		this._towerDisplay.addTower(this, scene, "Pidgey", 11, 0);
		this._towerDisplay.addTower(this, scene, "Pidgeotto", 12, 0);
		this._towerDisplay.addTower(this, scene, "Pidgeot", 13, 0);
		this._towerDisplay.addTower(this, scene, "Oddish", 14, 0);
		this._towerDisplay.addTower(this, scene, "Gloom", 14, 1);
		this._towerDisplay.addTower(this, scene, "Vileplume", 14, 2);
		
		//Enemies
		this._towerDisplay.addTower(this, scene, "Grimer", 0, 9);
		this._towerDisplay.addTower(this, scene, "Muk", 1, 9);
		this._towerDisplay.addTower(this, scene, "Koffing", 2, 9);
		this._towerDisplay.addTower(this, scene, "Weezing", 3, 9);
		this._towerDisplay.addTower(this, scene, "Ekans", 4, 9);
		this._towerDisplay.addTower(this, scene, "Arbok", 5, 9);
		this._towerDisplay.addTower(this, scene, "Spearow", 6, 9);
		this._towerDisplay.addTower(this, scene, "Fearow", 7, 9);
		this._towerDisplay.addTower(this, scene, "Meowth", 8, 9);
		this._towerDisplay.addTower(this, scene, "Persian", 9, 9);
		this._towerDisplay.addTower(this, scene, "Mewtwo", 10, 9);
		return scene;
	}
}
