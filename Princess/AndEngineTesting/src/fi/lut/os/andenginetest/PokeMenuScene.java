package fi.lut.os.andenginetest;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.util.color.Color;

import fi.lut.os.fonts.GradientFontFactory;
import fi.lut.os.fonts.GradientStrokeFont;

public class PokeMenuScene extends MenuScene {
	
	// Constants
	protected static final int MENU_START = 0;
	protected static final int MENU_OPTIONS = MENU_START + 1;
	protected static final int MENU_QUIT = MENU_START + 2;
	
	private PokeTowerGameActivity mGameActivity;
	private BitmapTextureAtlas menuFontTexture;
	
	private Font menuItemFont;
	private GradientStrokeFont menuGradienItemtFont;
	private BitmapTextureAtlas menuGradientFontTexture;
	
	public PokeMenuScene(PokeTowerGameActivity pGameActivity, Camera pCamera) {
		super(pCamera);
		
		mGameActivity = pGameActivity;
		loadFontTexture();
		
		final IMenuItem startMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_START, menuGradienItemtFont, "Start",
						mGameActivity.getVertexBufferObjectManager()), Color.BLUE,
				Color.WHITE);
		addMenuItem(startMenuItem);

		final IMenuItem optionsMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_OPTIONS, menuGradienItemtFont, "Options",
						mGameActivity.getVertexBufferObjectManager()), Color.BLUE,
				Color.WHITE);
		addMenuItem(optionsMenuItem);

		final IMenuItem quitMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_QUIT, menuGradienItemtFont, "Quit",
						mGameActivity.getVertexBufferObjectManager()), Color.BLUE,
				Color.WHITE);

		addMenuItem(quitMenuItem);

		buildAnimations();

		setBackgroundEnabled(false);

		setOnMenuItemClickListener(mGameActivity);
	}
	
	private void loadFontTexture(){
		// Load menu fonts to be used in menu
		menuFontTexture = new BitmapTextureAtlas(mGameActivity.getTextureManager(), 512,
				512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		mGameActivity.getTextureManager().loadTexture(menuFontTexture);
		menuItemFont = FontFactory.createFromAsset(mGameActivity.getFontManager(),
				menuFontTexture, mGameActivity.getAssets(), "font/SNAP____.TTF", 80f,
				true, android.graphics.Color.WHITE);
		mGameActivity.getFontManager().loadFonts(menuItemFont);

		menuGradientFontTexture = new BitmapTextureAtlas(
				mGameActivity.getTextureManager(), 512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mGameActivity.getTextureManager().loadTexture(menuGradientFontTexture);

		GradientFontFactory.setAssetBasePath("font/");
		menuGradienItemtFont = GradientFontFactory.createStrokeFromAsset(
				mGameActivity.getFontManager(), menuGradientFontTexture, mGameActivity.getAssets(),
				"SNAP____.TTF", 80f, true, new Color[] {
						new Color(1f, 0.43137254901960784313725490196078f,
								0f),
						new Color(1f, 1f, 0f),
						new Color(1f, 0.43137254901960784313725490196078f,
								0f) }, 5, Color.BLACK);
		menuGradienItemtFont.load();
	}
}
