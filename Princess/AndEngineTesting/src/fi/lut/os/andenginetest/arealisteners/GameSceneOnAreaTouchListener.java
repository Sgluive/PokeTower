package fi.lut.os.andenginetest.arealisteners;

import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.shape.IShape;
import org.andengine.input.touch.TouchEvent;

import fi.lut.os.andenginetest.HUDButtons;
import fi.lut.os.andenginetest.PokeTowerGameActivity;
import android.util.Log;

public class GameSceneOnAreaTouchListener implements IOnAreaTouchListener {

	PokeTowerGameActivity mGameActivity;
	HUDButtons ID;
	float startTime, endTime;

	public GameSceneOnAreaTouchListener(PokeTowerGameActivity pGameActivity) {
		mGameActivity = pGameActivity;
	}

	boolean touchMove = false;
	boolean touchUp = false;
	boolean touchDown = false;
	float lastCoordinateX;
	float lastCoordinateY;
	
	private void clearTouch(){
		touchDown = false;
		touchMove = false;
		touchUp = false;
		
		startTime = 0;
		
		lastCoordinateX = 0;
		lastCoordinateY = 0;
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {

		if(!touchDown){
			startTime = mGameActivity.getEngine().getSecondsElapsedTotal();
		}
		
		float mTimeDifference = mGameActivity.getEngine()
				.getSecondsElapsedTotal() - startTime;
		
		if (pSceneTouchEvent.isActionDown()) {
			lastCoordinateX = pTouchAreaLocalX;
			lastCoordinateY = pTouchAreaLocalY;
			touchDown = true;
			
		} else if (pSceneTouchEvent.isActionMove()) {
			if(Math.abs(lastCoordinateX - pTouchAreaLocalX) > 5.0 || (Math.abs(lastCoordinateY - pTouchAreaLocalY) > 5.0)){
				touchMove = true;
			}else {
				lastCoordinateX = pTouchAreaLocalX;
				lastCoordinateY = pTouchAreaLocalY;
			}
		} else if (pSceneTouchEvent.isActionUp()) {
			touchUp = true;
		}

		// Presses

		// HUD
		if (touchUp) {
			try {
				ID = (HUDButtons) ((IShape) pTouchArea).getUserData();
				switch (ID) {
				case MENU_BUTTON:
					mGameActivity.setDebugText("Debug: Pressed MENU!");
					mGameActivity.initMenu();
					clearTouch();
					break;
				case GAME_AREA:
					if (touchUp == true && touchMove == false
							&& mTimeDifference > 0.3) {
						clearTouch();
						mGameActivity.showTowerMenu(true, pSceneTouchEvent);
						mGameActivity.setDebugText("Debug: Pressed game area!");
						return true;
					} else {
						clearTouch();
						return false;
					}
//				case TOWER_MENU:
//					mGameActivity.setDebugText("Debug: Pressed tower menu!");
//					clearTouch();
//					break;
				default:
					return false;
				}

			} catch (ClassCastException e) {
				Log.e("AndEngine", e.getMessage());
				return false;
			} catch (NullPointerException e) {
				Log.d("AndEngine", "nULLPOINTER EXEPTION");
			}
		}
		return false;
	}

}
