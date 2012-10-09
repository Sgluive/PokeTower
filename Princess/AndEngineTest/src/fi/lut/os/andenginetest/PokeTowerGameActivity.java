package fi.lut.os.andenginetest;

import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.shape.IShape;
import org.andengine.engine.camera.Camera;
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
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.util.GLState;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;

import fi.lut.os.fonts.GradientFontFactory;
import fi.lut.os.fonts.GradientStrokeFont;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import java.io.IOException;
import java.io.InputStream;

public class PokeTowerGameActivity extends BaseGameActivity implements
		IOnSceneTouchListener, IOnMenuItemClickListener, IOnAreaTouchListener {

	// Scenes
	private Scene splashScene;
	private Scene gameScene;
	private MenuScene menuScene;

	// Constants
	protected static final int MENU_START = 0;
	protected static final int MENU_OPTIONS = MENU_START + 1;
	protected static final int MENU_QUIT = MENU_START + 2;

	protected static final int MENU_BUTTON_SPRITE = 0;

	// Texture atlas from where to load textures
	private BitmapTextureAtlas splashTextureAtlas;

	// Splashscreen textures
	private ITextureRegion splashTextureRegion;
	private Sprite splash;

	// Fonts
	private BitmapTextureAtlas menuFontTexture;
	private Font menuItemFont;

	// In-game sprites, textures and atlas
	private Sprite backgroundSprite;
	private Sprite pikachuSprite;
	private Sprite menuButtonSprite;
	private GradientStrokeFont menuGradienItemtFont;
	private BitmapTextureAtlas menuGradientFontTexture;

	// Texture regions for sprites
	private ITextureRegion mBackgroundTextureRegion, mPikachuTextureRegion,
			mMenuButtonTextureRegion;

	// Camera
	private Camera mCamera;
	private static int CAMERA_WIDTH = 1280;
	private static int CAMERA_HEIGHT = 720;

	// Scrolling - Variables used for screen scrolling
	private float mTouchX = 0, mTouchY = 0, mTouchOffsetX = 0,
			mTouchOffsetY = 0;

	// First thing to be called when you start AndEngine
	public EngineOptions onCreateEngineOptions() {
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	// Here one should load their resources needed for the game
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		Log.e("AndEngineTest", "Creating initial resources for splash screen!");
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("gfx/spritesheet/");
		splashTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 256,
				256, TextureOptions.DEFAULT);
		splashTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlas, this, "splash.png", 0, 0);
		splashTextureAtlas.load();

		// Remember the callback
		pOnCreateResourcesCallback.onCreateResourcesFinished();

	}

	// Method that creates the scene
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {

		initSplashScene();
		Log.e("AndEngineTest", "Creating scene!");
		pOnCreateSceneCallback.onCreateSceneFinished(this.splashScene);
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
						mEngine.setScene(gameScene);

					}
				}));
		// REMEMBER THE CALLBACK - IT'S REALLY IMPORTANT
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	private void loadScenes() {
		// load your game here, you scenes
		Log.e("AndEngineTest", "Loading scenes after splash!");
		gameScene = new Scene();
		backgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion,
				getVertexBufferObjectManager());
		gameScene.attachChild(backgroundSprite);

		pikachuSprite = new Sprite(50, 50, mPikachuTextureRegion,
				getVertexBufferObjectManager());
		gameScene.attachChild(pikachuSprite);

		// Menu button
		menuButtonSprite = new Sprite(10, 10, mMenuButtonTextureRegion,
				getVertexBufferObjectManager());
		menuButtonSprite.setScale(0.3f);
		menuButtonSprite.setScaleCenter(0f, 0f);
		menuButtonSprite.setUserData(MENU_BUTTON_SPRITE);
		gameScene.attachChild(menuButtonSprite);

		// Set listener to handle touch inputs
		gameScene.setOnSceneTouchListener(this);
		gameScene.setOnAreaTouchListener(this);
		gameScene.registerTouchArea(menuButtonSprite);

		// Initialize menu scene
		this.mEngine.registerUpdateHandler(new FPSLogger());

		menuScene = createMenuScene();

	}

	// Method to initialize splashscreen
	private void initSplashScene() {
		splashScene = new Scene();
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
		splashScene.attachChild(splash);
	}

	// Load resources for game scene (Not splashscreen)
	public void loadResources() {
		try {
			Log.e("AndEngineTest", "Loading resources!");
			// 1 - Set up bitmap textures
			ITexture backgroundTexture = new BitmapTexture(getTextureManager(),
					new IInputStreamOpener() {
						public InputStream open() throws IOException {
							return getAssets().open("gfx/grass_1.jpg");
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

			// Load menu fonts to be used in menu
			menuFontTexture = new BitmapTextureAtlas(getTextureManager(), 512,
					512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

			getTextureManager().loadTexture(menuFontTexture);
			menuItemFont = FontFactory.createFromAsset(getFontManager(),
					menuFontTexture, getAssets(), "font/SNAP____.TTF", 80f,
					true, android.graphics.Color.WHITE);
			getFontManager().loadFonts(menuItemFont);

			menuGradientFontTexture = new BitmapTextureAtlas(
					getTextureManager(), 512, 512,
					TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			getTextureManager().loadTexture(menuGradientFontTexture);

			GradientFontFactory.setAssetBasePath("font/");
			menuGradienItemtFont = GradientFontFactory.createStrokeFromAsset(
					getFontManager(), menuGradientFontTexture, getAssets(),
					"SNAP____.TTF", 80f, true, new Color[] {
							new Color(1f, 0.43137254901960784313725490196078f,
									0f),
							new Color(1f, 1f, 0f),
							new Color(1f, 0.43137254901960784313725490196078f,
									0f) }, 5, Color.BLACK);
			menuGradienItemtFont.load();
		} catch (IOException e) {
			Debug.e(e.getMessage());
		}
	}

	protected MenuScene createMenuScene() {
		final MenuScene menuScene = new MenuScene(mCamera);

		final IMenuItem startMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_START, menuGradienItemtFont, "Start",
						getVertexBufferObjectManager()), Color.BLUE,
				Color.WHITE);
		menuScene.addMenuItem(startMenuItem);

		final IMenuItem optionsMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_OPTIONS, menuGradienItemtFont, "Options",
						getVertexBufferObjectManager()), Color.BLUE,
				Color.WHITE);
		menuScene.addMenuItem(optionsMenuItem);

		final IMenuItem quitMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_QUIT, menuGradienItemtFont, "Quit",
						getVertexBufferObjectManager()), Color.BLUE,
				Color.WHITE);

		menuScene.addMenuItem(quitMenuItem);

		menuScene.buildAnimations();

		menuScene.setBackgroundEnabled(false);

		menuScene.setOnMenuItemClickListener(this);
		return menuScene;
	}
	
	private void createNewMonster(float positionX, float positionY){
		Sprite tempSprite = new Sprite(50, 50, mPikachuTextureRegion,
				getVertexBufferObjectManager());
		gameScene.attachChild(tempSprite);
		tempSprite.setPosition(positionX, positionY);
	}

	// Event to handle touchevents (Required as class implements
	// IOnSceneTouchListener)
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
			mTouchX = pSceneTouchEvent.getMotionEvent().getX();
			mTouchY = pSceneTouchEvent.getMotionEvent().getY();
//			pikachuSprite.setPosition(mTouchX, mTouchY);
			createNewMonster(mTouchX, mTouchY);
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
			gameScene.clearChildScene();
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

	private void initMenu() {
		if (gameScene.hasChildScene()) {
			/* Remove the menu and reset it. */
			menuScene.back();
		} else {
			/* Attach the menu. */
			gameScene.setChildScene(menuScene, false, true, true);
		}
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		try {
			Integer ID = (Integer) ((IShape) pTouchArea).getUserData();
			switch (ID) {
			case 0:
				initMenu();
				return true;
			default:
				return false;
			}
		} catch (ClassCastException cce) {
			Log.e("AndEngine", cce.getMessage());
			return false;
		}
	}
}
