package fi.lut.os.andenginetest.arealisteners;

import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.shape.IShape;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;
import fi.lut.os.andenginetest.HUDButtons;
import fi.lut.os.andenginetest.PokeTowerGameActivity;
import fi.lut.os.andenginetest.TowerMenu;
import fi.lut.os.andenginetest.TowerMenu.TowerType;

public class HUDOnAreaTouchListener implements IOnAreaTouchListener {

	TowerMenu mTowerMenu;
	PokeTowerGameActivity mGameActivity;
	HUDButtons ID;
	float startTime, endTime;

	public HUDOnAreaTouchListener(TowerMenu pTowerMenu,
			PokeTowerGameActivity pGameActivity) {
		mTowerMenu = pTowerMenu;
		mGameActivity = pGameActivity;
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionUp()) {
			mTowerMenu.showMenu(false);
			try {
				ID = (HUDButtons) ((IShape) pTouchArea).getUserData();
				if(ID == HUDButtons.MENU_BUTTON){
					mGameActivity.initMenu();
				}
				
				TowerType TOWER_TYPE = mTowerMenu.checkChild(new float[] {
						pSceneTouchEvent.getX(), pSceneTouchEvent.getY() });
				if (TOWER_TYPE != null) {
					mGameActivity.createNewMonster(
							mTowerMenu.getLastTouchEvent(), TOWER_TYPE);
				}
			} catch (ClassCastException e) {
				Log.d("AndEngine", "Class cast misbefaviour in HUDListener!");
			}
		}
		return false;
	}

}
