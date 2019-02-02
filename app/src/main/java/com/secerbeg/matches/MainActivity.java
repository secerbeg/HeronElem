package com.secerbeg.matches;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.secerbeg.matches.common.Shared;
import com.secerbeg.matches.engine.Engine;
import com.secerbeg.matches.engine.ScreenController;
import com.secerbeg.matches.engine.ScreenController.Screen;
import com.secerbeg.matches.events.EventBus;
import com.secerbeg.matches.events.ui.BackGameEvent;
import com.secerbeg.matches.ui.PopupManager;
import com.secerbeg.matches.utils.Utils;

public class MainActivity extends FragmentActivity
{
	private ImageView mBackgroundImage;

	/**
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Shared.context = getApplicationContext();
		Shared.engine = Engine.getInstance();
		Shared.eventBus = EventBus.getInstance();

		setContentView(R.layout.activity_main);
		mBackgroundImage = (ImageView) findViewById(R.id.background_image);

		Shared.activity = this;
		Shared.engine.start();
		Shared.engine.setBackgroundImageView(mBackgroundImage);

		// set background
		setBackgroundImage();

		// Initial view and setting up of  MENU
		ScreenController.getInstance().openScreen(Screen.MENU);
	}

	/**
	 *
	 */
	@Override
	protected void onDestroy()
	{
		Shared.engine.stop();
		super.onDestroy();
	}

	/**
	 *
	 */
	@Override
	public void onBackPressed()
	{
		if (PopupManager.isShown())
		{
			PopupManager.closePopup();
			if (ScreenController.getLastScreen() == Screen.GAME)
			{
				Shared.eventBus.notify(new BackGameEvent());
			}
		} else if (ScreenController.getInstance().onBack())
		{
			super.onBackPressed();
		}
	}

	/**
	 *
	 */
	private void setBackgroundImage()
	{
		Bitmap bitmap =
				Utils.scaleDown(R.drawable.background, Utils.screenWidth(), Utils.screenHeight());
		bitmap =
				Utils.crop(bitmap, Utils.screenHeight(), Utils.screenWidth());
		bitmap =
				Utils.downscaleBitmap(bitmap, 2);
		mBackgroundImage.setImageBitmap(bitmap);
	}

}
