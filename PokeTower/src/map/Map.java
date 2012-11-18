package map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import com.smartapp.poketower.MainActivity;

import display.TowerDisplay;

public class Map {

	//Lists
	private List<String> _map;
	private List<Sprite> _sprites;

	//Textures
	private ITextureRegion _TForbidden;
	private ITextureRegion _TBorder;
	private ITextureRegion _TPrincess;
	private ITextureRegion _TSpawner;
	private ITextureRegion _TPath;
	private ITextureRegion _TTowerPlace;
	private final int SQUARE_SIZE = 16;

	public Map()
	{
		_map = new ArrayList<String>();
		_sprites = new ArrayList<Sprite>();
	}

	public void loadMap(InputStream inputStream)
	{
		BufferedReader buff = null;
		InputStreamReader ipsr = null;
		String str = null;

		ipsr = new InputStreamReader(inputStream);
		buff = new BufferedReader(ipsr);
		try {
			while((str = buff.readLine()) != null){
				_map.add(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadSpawnerPos(int x, int y)
	{
		for (y = 0; y < _map.size(); y++)
		{
			if ((x = _map.get(y).indexOf('S')) != -1)
				return;
		}
		x = -1;
		y = -1;
	}
	public void loadPikachuPos(int x, int y)
	{
		for (y = 0; y < _map.size(); y++)
		{
			if ((x = _map.get(y).indexOf('P')) != -1)
				return;
		}
		x = -1;
		y = -1;
	}

	public boolean canPutTowerAt(int x, int y)
	{
		if (_map.get(y).charAt(x) == ' ')
			return true;
		return false;		
	}

	public int getHeight()
	{
		return (_map.size() * SQUARE_SIZE);
	}

	public int getWidth()
	{
		if (_map.size() == 0)
			return 0;
		return (_map.get(0).length() * SQUARE_SIZE);
	}

	public void loadRessources(final MainActivity main)
	{		
		ITexture TForbidden = TowerDisplay.loadTexture(main, "gfx/Forbidden.png");
		this._TForbidden = TextureRegionFactory.extractFromTexture(TForbidden);

		ITexture TPrincess = TowerDisplay.loadTexture(main, "gfx/Princess.png");
		this._TPrincess = TextureRegionFactory.extractFromTexture(TPrincess);

		ITexture TSpawner = TowerDisplay.loadTexture(main, "gfx/Spawner.png");
		this._TSpawner = TextureRegionFactory.extractFromTexture(TSpawner);

		ITexture TBorder = TowerDisplay.loadTexture(main, "gfx/Border.png");
		this._TBorder = TextureRegionFactory.extractFromTexture(TBorder);

		ITexture TPath = TowerDisplay.loadTexture(main, "gfx/Path.png");
		this._TPath = TextureRegionFactory.extractFromTexture(TPath);

		ITexture TTowerPlace = TowerDisplay.loadTexture(main, "gfx/TowerPlace.png");
		this._TTowerPlace = TextureRegionFactory.extractFromTexture(TTowerPlace);
	}

	public void displayMap(MainActivity main, Scene scene)
	{
		Sprite sprite;
		int x, y;


		for (y = 0; y < _map.size(); y++)
			for (x = 0; x < _map.get(y).length(); x++)
			{
				sprite = null;
				if (_map.get(y).charAt(x) == 'X')
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TForbidden, main.getVertexBufferObjectManager());
				else if (_map.get(y).charAt(x) == 'P')
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TPrincess, main.getVertexBufferObjectManager());
				else if (_map.get(y).charAt(x) == 'S')
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TSpawner, main.getVertexBufferObjectManager());
				else if (_map.get(y).charAt(x) == 'B')
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TBorder, main.getVertexBufferObjectManager());
				else if (_map.get(y).charAt(x) == '.')
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TPath, main.getVertexBufferObjectManager());
				else if (_map.get(y).charAt(x) == ' ')
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TTowerPlace, main.getVertexBufferObjectManager());
				if (sprite != null)
				{
					scene.attachChild(sprite);
					_sprites.add(sprite);
				}
			}
	}
	
	public void clearMap(Scene scene)
	{
		Sprite sprite;
		Iterator<Sprite> it = _sprites.iterator();
		while (it.hasNext())
		{
			sprite = it.next();
			scene.detachChild(sprite);
		}
		_sprites.clear();
		_map.clear();
	}

}
