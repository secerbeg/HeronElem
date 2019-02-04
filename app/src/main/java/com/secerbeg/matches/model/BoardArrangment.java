package com.secerbeg.matches.model;

import android.graphics.Bitmap;

import com.secerbeg.matches.common.Shared;
import com.secerbeg.matches.day.Days;
import com.secerbeg.matches.utils.Utils;

import java.util.Map;

/**
 * Before game starts, engine build new board
 * 
 * @author sromku
 */
public class BoardArrangment
{

	// like {0-2, 4-3, 1-5}
	public Map<Integer, Integer> pairs;
	// like {0-monsters_20, 1-monsters_12, 2-monsters_20, ...}
	public Map<Integer, String> tileUrls;

	/**
	 * 
	 * @param id
	 *            The id is the number between 0 and number of possible tiles of
	 *            this day
	 * @return The Bitmap of the tile
	 */
	public Bitmap getTileBitmap(int id, int size)
	{
		String string = tileUrls.get(id);
		if (string.contains(Days.URI_DRAWABLE))
		{
			String drawableResourceName = string.substring(Days.URI_DRAWABLE.length());
			int drawableResourceId =
					Shared.context.getResources().getIdentifier(
							drawableResourceName,
							"drawable",
							Shared.context.getPackageName());

			Bitmap bitmap = Utils.scaleDown(drawableResourceId, size, size);
			return Utils.crop(bitmap, size, size);
		}
		return null;
	}

	/**
	 *
	 * @param id1
	 * @param id2
	 * @return
	 */
	public boolean isPair(int id1, int id2)
	{
		Integer integer = pairs.get(id1);
		if (integer == null)
		{
			// TODO Report this bug!!!
			return false;
		}
		return integer.equals(id2);
	}

}
