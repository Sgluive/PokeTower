package fi.lut.os.andenginetest;

import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.util.GLState;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;

import fi.lut.os.andenginetest.TowerMenu.TowerType;
import fi.lut.os.andenginetest.arealisteners.GameSceneOnAreaTouchListener;
import fi.lut.os.andenginetest.arealisteners.HUDOnAreaTouchListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class PokeTowerGameActivity extends BaseGameActivity implements
		IOnSceneTouchListener, IOnMenuItemClickListener {

	// Scenes
	private Scene mSplashScene;
	private Scene mGameScene;
	private MenuScene mMenuScene;

	// HUD
	private HUD mHUD;

	// Listeners
	GameSceneOnAreaTouchListener mOnAreaTouchListener;
	HUDOnAreaTouchListener mHudOnAreaTouchListener;

	// Constants
	protected static final int MENU_START = 0;
	protected static final int MENU_OPTIONS = MENU_START + 1;
	protected static final int MENU_QUIT = MENU_START + 2;

	// Texture atlas from where to load textures
	private BitmapTextureAtlas splashTextureAtlas;

	// Splashscreen textures
	private ITextureRegion splashTextureRegion;
	private Sprite splash;

	// Fonts
	private Font menuItemFont;
	// private BitmapTextureAtlas menuFontTexture;

	// In-game sprites, textures and atlas
	private Sprite backgroundSprite;
	private Sprite pikachuSprite;
	private Sprite menuButtonSprite;
	// private GradientStrokeFont menuGradienItemtFont;
	// private BitmapTextureAtlas menuGradientFontTexture;

	// Texture regions for sprites
	private ITextureRegion mBackgroundTextureRegion, mPikachuTextureRegion,
			mMenuButtonTextureRegion, mMenuBannerTextureRegion;

	// TowerSelection Menu
	private TowerMenu mTowerMenu;

	private BitmapTextureAtlas menuFontTexture;

	// Music
	Music mGameMusic;

	// Camera
	private Camera mCamera;
	private static int CAMERA_WIDTH = 1280;
	private static int CAMERA_HEIGHT = 720;

	// Scrolling - Variables used for screen scrolling
	private float mTouchX = 0, mTouchY = 0, mTouchOffsetX = 0,
			mTouchOffsetY = 0;

	// Debugging text field
	Text debuggingText;
	Boolean DEBUG = false;
	private Sprite mBanner;

	// First thing to be called when you start AndEngine
	public EngineOptions onCreateEngineOptions() {
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		return engineOptions;
		// return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
		// new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	// Here one should load their resources needed for the game
	// But now this is used to load splashscreen and game resources are loaded
	// laters
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		Log.e("AndEngineTest", "Creating initial resources for splash screen!");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("gfx/spritesheet/");
		splashTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 512,
				512, TextureOptions.DEFAULT);
		splashTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlas, this, "splash.png", 0, 0);
		splashTextureAtlas.load();

		mEngine.getEngineOptions().getAudioOptions().setNeedsMusic(true);
		try {

			getEngine().getEngineOptions().getAudioOptions()
					.setNeedsMusic(true);
			MusicFactory.setAssetBasePath("mfx/");
			mGameMusic = MusicFactory.createMusicFromAsset(getMusicManager(),
					this, "Still alive.ogg");
		} catch (FileNotFoundException e) {
			Log.e("AndEngineTest", "Music file not found!: " + e);
		} catch (Exception e) {
			Log.e("AndEngineTest", "Music file load exeption or something!: "
					+ e);
		}
		// mGameMusic.setLooping(true);

		// Remember the callback
		pOnCreateResourcesCallback.onCreateResourcesFinished();

	}

	// Method that creates the scene
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {

		initSplashScene();
//		mGameMusic.play();
		Log.e("AndEngineTest", "Creating scene!");
		pOnCreateSceneCallback.onCreateSceneFinished(this.mSplashScene);
	}

	// Here one sets the assets to scene
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		Log.e("AndEngineTest", "Populating scene and creating TimerHandler!");

		// Update handler (TimerHander) is used to change from splash scene to
		// game after one second
		mEngine.registerUpdateHandler(new TimerHandler(1.0f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {

						Log.e("AndEngineTest", "Loading Menu!");

						mEngine.unregisterUpdateHandler(pTimerHandler);
						loadResources();
						loadScenes();
						splash.detachSelf();
						mEngine.setScene(mGameScene);

					}
				}));
		// REMEMBER THE CALLBACK - IT'S REALLY IMPORTANT
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	// Load resources for game scene (Not splashscreen)
	public void loadResources() {
		try {
			Log.e("AndEngineTest", "Loading resources!");
			// 1 - Set up bitmap textures
			ITexture backgroundTexture = new BitmapTexture(getTextureManager(),
					new IInputStreamOpener() {
						public InputStream open() throws IOException {
							return getAssets().open("gfx/map_1.jpg");
						}
					});
			ITexture picachuTexture = new BitmapTexture(getTextureManager(),
					new IInputStreamOpener() {
						public InputStream open() throws IOException {
							return getAssets().open("gfx/pikachu.png");
						}
					});
			ITexture menuButtonTexture = new BitmapTexture(getTextureManager(),
					new IInputStreamOpener() {

						@Override
						public InputStream open() throws IOException {
							return getAssets().open("gfx/menu_button.png");
						}
					});

//			ITexture mMenuBannerTexture = null;
//			mMenuBannerTexture = new BitmapTexture(getTextureManager(),
//					new IInputStreamOpener() {
//
//						@Override
//						public InputStream open() throws IOException {
//							return getAssets().open("gfx/menuIcons/banner.png");
//						}
//					});
//
//			mMenuBannerTexture.load();
//			mMenuBannerTextureRegion = TextureRegionFactory
//					.extractFromTexture(mMenuBannerTexture);

			// 2 - Load bitmap textures into VRAM
			backgroundTexture.load();
			picachuTexture.load();
			menuButtonTexture.load();
			mBackgroundTextureRegion = TextureRegionFactory
					.extractFromTexture(backgroundTexture);
			mPikachuTextureRegion = TextureRegionFactory
					.extractFromTexture(picachuTexture);
			mMenuButtonTextureRegion = TextureRegionFactory
					.extractFromTexture(menuButtonTexture);
		} catch (IOException e) {
			Debug.e(e.getMessage());
		}
	}

	private void loadScenes() {
		// load your game here, you scenes
		Log.e("AndEngineTest", "Loading scenes after splash!");

		// Game Scene
		mGameScene = new Scene();
		backgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion,
				getVertexBufferObjectManager());
		backgroundSprite.setSize(mCamera.getWidth(), mCamera.getHeight());
		backgroundSprite.setUserData(HUDButtons.GAME_AREA);
		mGameScene.attachChild(backgroundSprite);

		pikachuSprite = new Sprite(650, 360, mPikachuTextureRegion,
				getVertexBufferObjectManager());
		pikachuSprite.setScale(.3f);
		mGameScene.attachChild(pikachuSprite);

		// Menu button
		menuButtonSprite = new Sprite(10, 10, mMenuButtonTextureRegion,
				getVertexBufferObjectManager());
		menuButtonSprite.setScale(0.3f);
		menuButtonSprite.setScaleCenter(0f, 0f);
		menuButtonSprite.setUserData(HUDButtons.MENU_BUTTON);
		
		mOnAreaTouchListener = new GameSceneOnAreaTouchListener(this);
		mGameScene.setOnAreaTouchListener(mOnAreaTouchListener);
		
		//Create HUD
		createHUD();

		// Set listener to handle touch inputs
		mGameScene.setOnSceneTouchListener(this);
		mGameScene.registerTouchArea(backgroundSprite);

		// Create FPS logger
		this.mEngine.registerUpdateHandler(new FPSLogger());

	}

	public void createHUD() {
		// Create HUD and Listener for area touches
		mHUD = new HUD();
		mHUD.attachChild(menuButtonSprite);
		mHUD.registerTouchArea(menuButtonSprite);

		// TowerMenu
		mTowerMenu = new TowerMenu(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT,
				getVertexBufferObjectManager(), this);
		mTowerMenu.setUserData(HUDButtons.TOWER_MENU);
		mHUD.attachChild(mTowerMenu);
		mHUD.registerTouchArea(mTowerMenu);

		mHudOnAreaTouchListener = new HUDOnAreaTouchListener(mTowerMenu, this);
		mHUD.setOnAreaTouchListener(mHudOnAreaTouchListener);


		mCamera.setHUD(mHUD);
		// Create menu scene
		mMenuScene = new PokeMenuScene(this, mCamera);
		

		// Add debugging text field to HUD
		menuFontTexture = new BitmapTextureAtlas(getTextureManager(), 512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		menuItemFont = FontFactory.createFromAsset(getFontManager(),
				menuFontTexture, getAssets(), "font/SNAP____.TTF", 80f, true,
				android.graphics.Color.WHITE);
		debuggingText = new Text(10, mCamera.getHeight() - 100, menuItemFont,
				"Debug: ", 50, getVertexBufferObjectManager());
		if (DEBUG) {
			mHUD.attachChild(debuggingText);
		}
		setDebugText("HUD touch areas:,"
				+ String.valueOf(mHUD.getTouchAreas().size()));
	}

	public void setDebugText(String message) {
		debuggingText.setText(message);
	}

	// Method to initialize splashscreen
	private void initSplashScene() {
		mSplashScene = new Scene();
		splash = new Sprite(0, 0, splashTextureRegion,
				mEngine.getVertexBufferObjectManager()) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};

		splash.setScale(1.5f);
		splash.setPosition((CAMERA_WIDTH - splash.getWidth()) * 0.5f,
				(CAMERA_HEIGHT - splash.getHeight()) * 0.5f);
		mSplashScene.attachChild(splash);
	}

	public void createNewMonster(float[] coordinates, TowerType pType) {
		float positionX = coordinates[0];
		float positionY = coordinates[1];
		Log.d("AndEngine", positionX + "," + positionY);
		Sprite tempSprite = new Sprite(positionX
				- mPikachuTextureRegion.getWidth() / 2, positionY
				- mPikachuTextureRegion.getHeight() / 2, mPikachuTextureRegion,
				getVertexBufferObjectManager());
		mGameScene.attachChild(tempSprite);
	}

	// Event to handle touchevents (Required as class implements
	// IOnSceneTouchListener)
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
			mTouchX = pSceneTouchEvent.getMotionEvent().getX();
			mTouchY = pSceneTouchEvent.getMotionEvent().getY();
		} else if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_MOVE) {
			float newX = pSceneTouchEvent.getMotionEvent().getX();
			float newY = pSceneTouchEvent.getMotionEvent().getY();

			mTouchOffsetX = (newX - mTouchX);
			mTouchOffsetY = (newY - mTouchY);

			float newScrollX = this.mCamera.getCenterX() - mTouchOffsetX;
			float newScrollY = this.mCamera.getCenterY() - mTouchOffsetY;

			this.mCamera.setCenter(newScrollX, newScrollY);

			mTouchX = newX;
			mTouchY = newY;
		}
		return true;
	}

	// Implementation of IonMenuItemClick
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_START:
			mGameScene.clearChildScene();
			return true;
		case MENU_QUIT:
			finish();
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if (pKeyCode == KeyEvent.KEYCODE_BACK
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			initMenu();
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}

	public void initMenu() {
		if (mGameScene.hasChildScene()) {
			/* Remove the menu and reset it. */
			mMenuScene.back();
		} else {
			/* Attach the menu. */
			mGameScene.setChildScene(mMenuScene, false, true, true);
		}
	}
//
	public void showTowerMenu(boolean show, TouchEvent pTouchEvent) {
		mTowerMenu.showMenu(show, pTouchEvent);
//		if (show) {
//			// Slide background in the middle
//			mTowerMenu.registerEntityModifier(new MoveModifier(.2f, mCamera
//					.getWidth(), 100, 100, 100));
//			mBanner.registerEntityModifier(new MoveModifier(.2f, mCamera
//					.getWidth() / 3, mCamera.getWidth() / 3, -mBanner
//					.getHeight(), 2));
//		} else {
//			// Slide background away
//			mTowerMenu.registerEntityModifier(new MoveModifier(.2f, 100,
//					mCamera.getWidth(), 100, 100));
//			mBanner.registerEntityModifier(new MoveModifier(.2f, mCamera
//					.getWidth() / 3, mCamera.getWidth() / 3, mBanner.getY(),
//					-mBanner.getHeight()));
//			// mBanner.setVisible(false);
//		}
	}
}
