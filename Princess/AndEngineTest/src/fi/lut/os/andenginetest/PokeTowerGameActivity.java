package fi.lut.os.andenginetest;

import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
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
import org.andengine.input.touch.TouchEvent;

import android.util.Log;
import android.view.MotionEvent;

import java.io.IOException;
import java.io.InputStream;

public class PokeTowerGameActivity extends BaseGameActivity implements IOnSceneTouchListener {
	
	// Scenes
	private Scene splashScene;
	private Scene gameMenuScene;

	// Texture atlas from where to load textures
	private BitmapTextureAtlas splashTextureAtlas;
	
	// Splashscreen textures
	private ITextureRegion splashTextureRegion;
	private Sprite splash;
	
	// In-game textures
	Sprite backgroundSprite;
	Sprite pikachuSprite;

	// Texture regions for sprites
	private ITextureRegion mBackgroundTextureRegion, mPikachuTextureRegion;

	//Camera
	private Camera mCamera;
	private static int CAMERA_WIDTH = 1280;
	private static int CAMERA_HEIGHT = 720;
	
	// Scrolling - Variables used for screen scrolling
	private float mTouchX = 0, mTouchY = 0, mTouchOffsetX = 0, mTouchOffsetY = 0;


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
		splashTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),
				256, 256, TextureOptions.DEFAULT);
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

		// Update handler (TimerHander) is used to change from splash scene to game after one second
		mEngine.registerUpdateHandler(new TimerHandler(1.0f,
				new ITimerCallback() {
			
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {

						Log.e("AndEngineTest", "Loading Menu!");

						mEngine.unregisterUpdateHandler(pTimerHandler);
						loadResources();
						loadScenes();
						splash.detachSelf();
						mEngine.setScene(gameMenuScene);

					}
				}));
		// REMEMBER THE CALLBACK - IT'S REALLY IMPORTANT
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	
	private void loadScenes() {
		// load your game here, you scenes
		Log.e("AndEngineTest", "Loading scenes after splash!");
		gameMenuScene = new Scene();
		backgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
		pikachuSprite = new Sprite(50, 50, mPikachuTextureRegion, getVertexBufferObjectManager());
		gameMenuScene.attachChild(backgroundSprite);
		gameMenuScene.attachChild(pikachuSprite);
		
		// Set listener to handle touch inputs
		gameMenuScene.setOnSceneTouchListener(this);

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
			ITexture backgroundTexture = new BitmapTexture(
					this.getTextureManager(), new IInputStreamOpener() {
						public InputStream open() throws IOException {
							return getAssets().open("gfx/grass_1.jpg");
						}
					});
			ITexture picachuTexture = new BitmapTexture(
					this.getTextureManager(), new IInputStreamOpener() {
						public InputStream open() throws IOException {
							return getAssets().open("gfx/pikachu.png");
						}
					});

			// 2 - Load bitmap textures into VRAM
			backgroundTexture.load();
			picachuTexture.load();
			this.mBackgroundTextureRegion = TextureRegionFactory
					.extractFromTexture(backgroundTexture);
			this.mPikachuTextureRegion = TextureRegionFactory
					.extractFromTexture(picachuTexture);
		} catch (IOException e) {
			Debug.e(e);
		}
	}

	// Event to handle touchevents (Required as class inplements IOnSceneTouchListener)
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
	     if(pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN)
	        {
	                mTouchX = pSceneTouchEvent.getMotionEvent().getX();
	                mTouchY = pSceneTouchEvent.getMotionEvent().getY();
	                pikachuSprite.setPosition(mTouchX, mTouchY);
	        }
	        else if(pSceneTouchEvent.getAction() == MotionEvent.ACTION_MOVE)
	        {
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



}
