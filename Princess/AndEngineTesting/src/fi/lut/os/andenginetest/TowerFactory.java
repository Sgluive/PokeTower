package fi.lut.os.andenginetest;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import fi.lut.os.andenginetest.TowerMenu.TowerType;


public class TowerFactory {
	
	public static PokemonTower TowerFactory(final TowerType pTowerType, final PokeTowerGameActivity mGameActivity, float pPositionX, float pPositionY){
		String mPath = "gfx/";
		switch (pTowerType) {
		case BULBASAUR:
			mPath += "bulbasaur.png";
			break;
		case CHARMANDER:
			mPath += "charmander.png";
			break;
		case SQUIRTLE:
			mPath += "squirtle.png";
			break;
		default:
			break;
		}

		
		ITexture mTexture = null;
		final String mFinalPath = mPath;
		try {
			mTexture = new BitmapTexture(mGameActivity.getTextureManager(),
					new IInputStreamOpener() {
				public InputStream open() throws IOException {
					return mGameActivity.getAssets().open(mFinalPath);
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ITextureRegion mTextureRegion = TextureRegionFactory.extractFromTexture(mTexture);
		
		return new PokemonTower(pPositionX, pPositionY, mTextureRegion, mGameActivity.getVertexBufferObjectManager());
	}
}
