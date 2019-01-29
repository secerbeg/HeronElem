package com.secerbeg.matches;

import android.app.Application;

import com.secerbeg.matches.utils.FontLoader;

public class GameApplication extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		FontLoader.loadFonts(this);

	}
}
