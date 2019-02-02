package com.secerbeg.matches.themes;

import android.graphics.Bitmap;

import com.secerbeg.matches.common.Shared;
import com.secerbeg.matches.utils.Utils;

import java.util.ArrayList;

public class Themes
{
	public static String URI_DRAWABLE = "drawable://";

	public static Theme createDefaultWordsTheme()
	{
		Theme theme = new Theme();
		theme.id = 1;
		theme.name = "Words" ;
		theme.backgroundImageUrl = URI_DRAWABLE + "back_words";
		return theme;
	}

	/**
	 *  Creates Monday Theme and assembles Images associated with .png files
	 *
	 * @return @{@link Theme}
	 */
	public static Theme createMondayWordsTheme()
	{
		Theme theme = new Theme();
		theme.id = 2;
		theme.name = "Monday Words" ;
		theme.backgroundImageUrl = URI_DRAWABLE + "back_words";
		theme.tileImageUrls = new ArrayList<String>();

		for (int i = 1; i <= 24; i++)
		{
			theme.tileImageUrls.add(URI_DRAWABLE + String.format("week26mon_%d", i));
		}
		return theme;
	}

	public static Theme createWednesdayWordsTheme()
	{
		Theme theme = new Theme();
		theme.id = 3;
		theme.name = "Wednesday Words" ;
		theme.backgroundImageUrl = URI_DRAWABLE + "back_words";
		theme.tileImageUrls = new ArrayList<String>();

		for (int i = 1; i <= 27; i++)
		{
			theme.tileImageUrls.add(URI_DRAWABLE + String.format("week26wed_%d", i));
		}
		return theme;
	}


	public static Theme createMonsterTheme()
	{
		Theme theme = new Theme();
		theme.id = 4;
		theme.name = "Monsters";
		theme.backgroundImageUrl = URI_DRAWABLE + "back_horror";
		theme.tileImageUrls = new ArrayList<String>();
		// 40 drawables
		for (int i = 1; i <= 40; i++)
		{
			theme.tileImageUrls.add(URI_DRAWABLE + String.format("mosters_%d", i));
		}
		return theme;
	}


	public static Bitmap getBackgroundImage(Theme theme)
	{
		String drawableResourceName =
				theme.backgroundImageUrl.substring(Themes.URI_DRAWABLE.length());
		int drawableResourceId =
				Shared.context.getResources().getIdentifier(
						drawableResourceName,
						"drawable",
						Shared.context.getPackageName());
		Bitmap bitmap =
				Utils.scaleDown(
						drawableResourceId,
						Utils.screenWidth(),
						Utils.screenHeight());
		return bitmap;
	}

}