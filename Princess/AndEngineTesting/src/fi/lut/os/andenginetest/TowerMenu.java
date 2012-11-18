package fi.lut.os.andenginetest;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.io.in.IInputStreamOpener;

public class TowerMenu extends Rectangle {

	private PokeTowerGameActivity mGameActivity;

	private Sprite mBulbasaurs, mSquirtle, mCharmeleon;

	private float[] mTriggerPointTouchEvent;

	private Sprite mBanner;
	private Rectangle mMenuRectangle;

	private static final int ICON_HEIGHT = 150;

	public TowerMenu(float pX, float pY, float pWidth, float pHeight,
			VertexBufferObjectManager pRectangleVertexBufferManager,
			PokeTowerGameActivity pPokeTowerGameActivity) {
		super(pWidth, 0, pWidth, pHeight, pRectangleVertexBufferManager);

		mWidth = pPokeTowerGameActivity.getEngine().getEngineOptions()
				.getCamera().getWidth();
		mHeight = pPokeTowerGameActivity.getEngine().getEngineOptions()
				.getCamera().getHeight();

		setColor(0.0f, 0.0f, 0.0f, .3f);
		mGameActivity = pPokeTowerGameActivity;
		createMenu();
	}

	private void createMenu() {
		mMenuRectangle = new Rectangle(100, 100, mWidth - 200, mHeight - 200,
				mGameActivity.getVertexBufferObjectManager());
		mMenuRectangle.setColor(.1f, .1f, .1f, .6f);
		attachChild(mMenuRectangle);
		createMenuItems();
	}

	private void createMenuItems() {

		ITexture mBorderTexture = null;
		ITexture mMenuBannerTexture = null;

		try {
			mMenuBannerTexture = new BitmapTexture(
					mGameActivity.getTextureManager(),
					new IInputStreamOpener() {

						@Override
						public InputStream open() throws IOException {
							return mGameActivity.getAssets().open(
									"gfx/menuIcons/banner.png");
						}
					});

			mBorderTexture = new BitmapTexture(
					mGameActivity.getTextureManager(),
					new IInputStreamOpener() {
						public InputStream open() throws IOException {
							return mGameActivity.getAssets().open(
									"gfx/menuIcons/border.png");
						}
					});

			mMenuBannerTexture.load();
			mBorderTexture.load();
		} catch (IOException e) {
			e.printStackTrace();
			mGameActivity.finish();
		}

		// Border
		Sprite border = new Sprite(0, 0, mWidth - 200,mHeight - 200,
				TextureRegionFactory.extractFromTexture(mBorderTexture),
				mGameActivity.getVertexBufferObjectManager());
		mMenuRectangle.attachChild(border);

		// Banner for menu
		mBanner = new Sprite(0, 0, TextureRegionFactory.extractFromTexture(mMenuBannerTexture),
				getVertexBufferObjectManager());
		mBanner.setWidth(mGameActivity.getEngine().getCamera().getWidth() / 3);
		float mScaledownRatio = mBanner.getWidth()
				/ (mGameActivity.getEngine().getCamera().getWidth() / 3);
		mBanner.setSize(mGameActivity.getEngine().getCamera().getWidth() / 3,
				mBanner.getHeight() / mScaledownRatio);
		mBanner.setY(-mBanner.getHeight());
		this.attachChild(mBanner);

		// Icons
		mBulbasaurs = createIcon(TowerType.BULBASAUR, 1, 1);
		mCharmeleon = createIcon(TowerType.CHARMANDER, 2, 1);
		mSquirtle = createIcon(TowerType.SQUIRTLE, 3, 1);

		mMenuRectangle.attachChild(mBulbasaurs);
		mMenuRectangle.attachChild(mCharmeleon);
		mMenuRectangle.attachChild(mSquirtle);

	}

	private Sprite createIcon(TowerType pType, int pRow, int pCell) {
		Sprite temp = new Sprite(pCell * ICON_HEIGHT - 100, pRow * ICON_HEIGHT
				- 120, ICON_HEIGHT, ICON_HEIGHT, LoadIconTextureRegion(pType),
				getVertexBufferObjectManager());
		temp.setUserData(pType);
		return temp;
	}

	private ITextureRegion LoadIconTextureRegion(TowerType type) {
		String mTexturePath = null;
		ITexture mTexture = null;
		switch (type) {
		case SQUIRTLE:
			mTexturePath = "gfx/menuIcons/squirtle.png";
			break;
		case CHARMANDER:
			mTexturePath = "gfx/menuIcons/charmander.png";
			break;
		case BULBASAUR:
			mTexturePath = "gfx/menuIcons/bulbasaur.png";
			break;
		default:
			break;
		}
		try {
			final String mFinalTexturePath = mTexturePath;
			mTexture = new BitmapTexture(mGameActivity.getTextureManager(),
					new IInputStreamOpener() {
						public InputStream open() throws IOException {
							return mGameActivity.getAssets().open(
									mFinalTexturePath);
						}
					});
			mTexture.load();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return TextureRegionFactory.extractFromTexture(mTexture);
	}

	public TowerType checkChild(float coordinates[]) {
		float mX = coordinates[0];
		float mY = coordinates[1];

		for (Sprite sprite : getAllChildrens()) {
			if (sprite.contains(mX, mY)) {
				return (TowerType) sprite.getUserData();
			}
		}
		return null;
	}

	public Sprite[] getAllChildrens() {
		Sprite[] mSpriteList = new Sprite[getChildCount()];
		for (int i = 0; i < getChildCount(); i++) {
			mSpriteList[i] = (Sprite) getChildByIndex(i);
		}
		return mSpriteList;
	}

	public enum TowerType {
		SQUIRTLE, CHARMANDER, BULBASAUR

	}

	public void showMenu(boolean show, TouchEvent pTriggerPoint) {
		if (show) {
			mTriggerPointTouchEvent = new float[2];
			mTriggerPointTouchEvent[0] = pTriggerPoint.getX();
			mTriggerPointTouchEvent[1] = pTriggerPoint.getY();
		} else {
			mTriggerPointTouchEvent = null;
		}
		showMenu(show);
	}

	public void showMenu(boolean show) {
		Camera mCamera = mGameActivity.getEngine().getCamera();
		if (show) {
			// Slide evrything in the middle
			setPosition(0, 0);
			mMenuRectangle.registerEntityModifier(new MoveModifier(.2f, mCamera.getWidth(),
					100, 100, 100));
			mBanner.registerEntityModifier(new MoveModifier(.2f, mCamera
					.getWidth() / 3, mCamera.getWidth() / 3, -mBanner
					.getHeight(), 10));
		} else {
			// Slide evertyhing away
			setPosition(mWidth, 0);
			mMenuRectangle.registerEntityModifier(new MoveModifier(.2f, 100,
					mCamera.getWidth(), 100, 100));
			mBanner.registerEntityModifier(new MoveModifier(.2f, mCamera
					.getWidth() / 3, mCamera.getWidth() / 3, 10, -mBanner.getHeight()));
		}
	}

	public float[] getLastTouchEvent() {
		return mTriggerPointTouchEvent;
	}
}
