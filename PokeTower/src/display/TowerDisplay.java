package display;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import com.smartapp.poketower.MainActivity;

public class TowerDisplay {

	private Map<String, Sprite> _sprites;

	//Textures
	private ITextureRegion _TBulbasaur;
	private ITextureRegion _TIvysaur;
	private ITextureRegion _TVenusaur;
	private ITextureRegion _TCharmander;
	private ITextureRegion _TCharmeleon;
	private ITextureRegion _TCharizard;
	private ITextureRegion _TSquirtle;
	private ITextureRegion _TWartortle;
	private ITextureRegion _TBlastoise;
	private ITextureRegion _TMagnemite;
	private ITextureRegion _TMagneton;
	private ITextureRegion _TPidgey;
	private ITextureRegion _TPidgeotto;
	private ITextureRegion _TPidgeot;
	private ITextureRegion _TOddish;
	private ITextureRegion _TGloom;
	private ITextureRegion _TVileplume;
	private ITextureRegion _TGrimer;
	private ITextureRegion _TMuk;
	private ITextureRegion _TKoffing;
	private ITextureRegion _TWeezing;
	private ITextureRegion _TEkans;
	private ITextureRegion _TArbok;
	private ITextureRegion _TSpearow;
	private ITextureRegion _TFearow;
	private ITextureRegion _TMeowth;
	private ITextureRegion _TPersian;
	private ITextureRegion _TMewtwo;
	private final int SQUARE_SIZE = 16;

	public TowerDisplay()
	{
		_sprites = new HashMap<String, Sprite>();
	}

	public void loadRessources(final MainActivity main)
	{
		ITexture TBulbasaur = TowerDisplay.loadTexture(main, "gfx/Bulbasaur.png");
		this._TBulbasaur = TextureRegionFactory.extractFromTexture(TBulbasaur);
		ITexture TIvysaur = TowerDisplay.loadTexture(main, "gfx/Ivysaur.png");
		this._TIvysaur = TextureRegionFactory.extractFromTexture(TIvysaur);
		ITexture TVenusaur = TowerDisplay.loadTexture(main, "gfx/Venusaur.png");
		this._TVenusaur = TextureRegionFactory.extractFromTexture(TVenusaur);
		ITexture TCharmander = TowerDisplay.loadTexture(main, "gfx/Charmander.png");
		this._TCharmander = TextureRegionFactory.extractFromTexture(TCharmander);
		ITexture TCharmeleon = TowerDisplay.loadTexture(main, "gfx/Charmeleon.png");
		this._TCharmeleon = TextureRegionFactory.extractFromTexture(TCharmeleon);
		ITexture TCharizard = TowerDisplay.loadTexture(main, "gfx/Charizard.png");
		this._TCharizard = TextureRegionFactory.extractFromTexture(TCharizard);
		ITexture TSquirtle = TowerDisplay.loadTexture(main, "gfx/Squirtle.png");
		this._TSquirtle = TextureRegionFactory.extractFromTexture(TSquirtle);
		ITexture TWartortle = TowerDisplay.loadTexture(main, "gfx/Wartortle.png");
		this._TWartortle = TextureRegionFactory.extractFromTexture(TWartortle);
		ITexture TBlastoise = TowerDisplay.loadTexture(main, "gfx/Blastoise.png");
		this._TBlastoise = TextureRegionFactory.extractFromTexture(TBlastoise);
		ITexture TMagnemite = TowerDisplay.loadTexture(main, "gfx/Magnemite.png");
		this._TMagnemite = TextureRegionFactory.extractFromTexture(TMagnemite);
		ITexture TMagneton = TowerDisplay.loadTexture(main, "gfx/Magneton.png");
		this._TMagneton = TextureRegionFactory.extractFromTexture(TMagneton);
		ITexture TPidgey = TowerDisplay.loadTexture(main, "gfx/Pidgey.png");
		this._TPidgey = TextureRegionFactory.extractFromTexture(TPidgey);
		ITexture TPidgeotto = TowerDisplay.loadTexture(main, "gfx/Pidgeotto.png");
		this._TPidgeotto = TextureRegionFactory.extractFromTexture(TPidgeotto);
		ITexture TPidgeot = TowerDisplay.loadTexture(main, "gfx/Pidgeot.png");
		this._TPidgeot = TextureRegionFactory.extractFromTexture(TPidgeot);
		ITexture TOddish = TowerDisplay.loadTexture(main, "gfx/Oddish.png");
		this._TOddish = TextureRegionFactory.extractFromTexture(TOddish);
		ITexture TGloom = TowerDisplay.loadTexture(main, "gfx/Gloom.png");
		this._TGloom = TextureRegionFactory.extractFromTexture(TGloom);
		ITexture TVileplume = TowerDisplay.loadTexture(main, "gfx/Vileplume.png");
		this._TVileplume = TextureRegionFactory.extractFromTexture(TVileplume);
		ITexture TGrimer = TowerDisplay.loadTexture(main, "gfx/Grimer.png");
		this._TGrimer = TextureRegionFactory.extractFromTexture(TGrimer);
		ITexture TMuk = TowerDisplay.loadTexture(main, "gfx/Muk.png");
		this._TMuk = TextureRegionFactory.extractFromTexture(TMuk);
		ITexture TKoffing = TowerDisplay.loadTexture(main, "gfx/Koffing.png");
		this._TKoffing = TextureRegionFactory.extractFromTexture(TKoffing);
		ITexture TWeezing = TowerDisplay.loadTexture(main, "gfx/Weezing.png");
		this._TWeezing = TextureRegionFactory.extractFromTexture(TWeezing);
		ITexture TEkans = TowerDisplay.loadTexture(main, "gfx/Ekans.png");
		this._TEkans = TextureRegionFactory.extractFromTexture(TEkans);
		ITexture TArbok = TowerDisplay.loadTexture(main, "gfx/Arbok.png");
		this._TArbok = TextureRegionFactory.extractFromTexture(TArbok);
		ITexture TSpearow = TowerDisplay.loadTexture(main, "gfx/Spearow.png");
		this._TSpearow = TextureRegionFactory.extractFromTexture(TSpearow);
		ITexture TFearow = TowerDisplay.loadTexture(main, "gfx/Fearow.png");
		this._TFearow = TextureRegionFactory.extractFromTexture(TFearow);
		ITexture TMeowth = TowerDisplay.loadTexture(main, "gfx/Meowth.png");
		this._TMeowth = TextureRegionFactory.extractFromTexture(TMeowth);
		ITexture TPersian = TowerDisplay.loadTexture(main, "gfx/Persian.png");
		this._TPersian = TextureRegionFactory.extractFromTexture(TPersian);
		ITexture TMewtwo = TowerDisplay.loadTexture(main, "gfx/Mewtwo.png");
		this._TMewtwo = TextureRegionFactory.extractFromTexture(TMewtwo);
	}

	public static ITexture loadTexture(final MainActivity main, final String path)
	{
		ITexture texture = null;
		try {
			texture = new BitmapTexture(main.getTextureManager(), new IInputStreamOpener() {
				public InputStream open() throws IOException {
					return (main.getAssets().open(path));
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		texture.load();

		return texture;		
	}

	public void addTower(final MainActivity main, Scene scene, String name, int x, int y)
	{
		Sprite sprite = null;
		String tmp;
		int i;

		for (i = 0; ;i++)
		{
			tmp = name + String.valueOf(i);
			if (_sprites.get(tmp) == null)
			{
				if (name == "Bulbasaur")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TBulbasaur, main.getVertexBufferObjectManager());
				else if (name == "Ivysaur")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TIvysaur, main.getVertexBufferObjectManager());
				else if (name == "Venusaur")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TVenusaur, main.getVertexBufferObjectManager());
				else if (name == "Charmander")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TCharmander, main.getVertexBufferObjectManager());
				else if (name == "Charmeleon")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TCharmeleon, main.getVertexBufferObjectManager());
				else if (name == "Charizard")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TCharizard, main.getVertexBufferObjectManager());
				else if (name == "Squirtle")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TSquirtle, main.getVertexBufferObjectManager());
				else if (name == "Wartortle")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TWartortle, main.getVertexBufferObjectManager());
				else if (name == "Blastoise")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TBlastoise, main.getVertexBufferObjectManager());
				else if (name == "Magnemite")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TMagnemite, main.getVertexBufferObjectManager());
				else if (name == "Magneton")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TMagneton, main.getVertexBufferObjectManager());
				else if (name == "Pidgey")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TPidgey, main.getVertexBufferObjectManager());
				else if (name == "Pidgeotto")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TPidgeotto, main.getVertexBufferObjectManager());
				else if (name == "Pidgeot")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TPidgeot, main.getVertexBufferObjectManager());
				else if (name == "Oddish")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TOddish, main.getVertexBufferObjectManager());
				else if (name == "Gloom")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TGloom, main.getVertexBufferObjectManager());
				else if (name == "Vileplume")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TVileplume, main.getVertexBufferObjectManager());
				else if (name == "Grimer")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TGrimer, main.getVertexBufferObjectManager());
				else if (name == "Muk")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TMuk, main.getVertexBufferObjectManager());
				else if (name == "Koffing")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TKoffing, main.getVertexBufferObjectManager());
				else if (name == "Weezing")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TWeezing, main.getVertexBufferObjectManager());
				else if (name == "Ekans")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TEkans, main.getVertexBufferObjectManager());
				else if (name == "Arbok")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TArbok, main.getVertexBufferObjectManager());
				else if (name == "Spearow")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TSpearow, main.getVertexBufferObjectManager());
				else if (name == "Fearow")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TFearow, main.getVertexBufferObjectManager());
				else if (name == "Meowth")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TMeowth, main.getVertexBufferObjectManager());
				else if (name == "Persian")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TPersian, main.getVertexBufferObjectManager());
				else if (name == "Mewtwo")
					sprite = new Sprite(x * SQUARE_SIZE, y * SQUARE_SIZE, this._TMewtwo, main.getVertexBufferObjectManager());
				if (sprite != null)
				{
					scene.attachChild(sprite);
					sprite.setScaleCenter(0, 0);
					sprite.setScale((float) 0.4);
					_sprites.put(tmp, sprite);
				}
				break;

			}
		}

	}

	public void clearDisplay(Scene scene)
	{
		Sprite sprite;
		String key;
		Set<String> keyset = _sprites.keySet();
		Iterator<String> it = keyset.iterator();

		while (it.hasNext())
		{
			key = it.next();
			sprite = _sprites.get(key);
			scene.detachChild(sprite);
		}
		_sprites.clear();
	}

	public void removeTower(Scene scene, String key)
	{
		Sprite sprite;

		sprite = _sprites.get(key);
		scene.detachChild(sprite);
		_sprites.remove(key);
	}

	public void moveTower(String key, float x, float y)
	{
		Sprite sprite;

		sprite = _sprites.get(key);
		sprite.setPosition(x, y);
	}

}
