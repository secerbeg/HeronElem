package com.secerbeg.matches.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.secerbeg.matches.R;
import com.secerbeg.matches.common.Memory;
import com.secerbeg.matches.common.Shared;
import com.secerbeg.matches.events.ui.ThemeSelectedEvent;
import com.secerbeg.matches.themes.Theme;
import com.secerbeg.matches.themes.Themes;

import java.util.Locale;

public class ThemeSelectFragment extends Fragment
{
	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState)
	{
		View view =
				LayoutInflater.from(Shared.context).inflate(
						R.layout.theme_select_fragment,
						container,
						false);

		View monsters =
				this.preapareMonstersViewAndTheme(view);

		View words =
				this.preapareWordsViewAndTheme(view);

		animateShow(monsters);
		animateShow(words);

		return view;
	}



	/**
	 *
	 * @param view
	 */
	private void animateShow(View view)
	{
		ObjectAnimator animatorScaleX =
				ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
		ObjectAnimator animatorScaleY =
				ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setDuration(300);
		animatorSet.playTogether(animatorScaleX, animatorScaleY);
		animatorSet.setInterpolator(new DecelerateInterpolator(2));
		view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		animatorSet.start();
	}

	/**
	 *
	 * @param imageView
	 * @param theme
	 * @param type
	 */
	private void setStars(ImageView imageView, Theme theme, String type)
	{
		int sum = 0;
		for (int difficulty = 1; difficulty <= 6; difficulty++)
		{
			sum += Memory.getHighStars(theme.id, difficulty);
		}
		int num = sum / 6;
		if (num != 0)
		{
			String drawableResourceName =
					String.format(Locale.US, type + "_theme_star_%d", num);

			int drawableResourceId =
					Shared.context.getResources().getIdentifier(
							drawableResourceName,
							"drawable",
							Shared.context.getPackageName());

			imageView.setImageResource(drawableResourceId);
		}
	}

	/**
	 *
	 * @param view
	 * @return
	 */
	private View  preapareWordsViewAndTheme (View view)
	{
		View words = view.findViewById(R.id.theme_words_container);
		final Theme themeWords = Themes.createDefaultWordsTheme();

		return invokeOnClickListener(words,themeWords);
	}

	/**
	 *
	 * @param view
	 */
	private View preapareMonstersViewAndTheme(View view)
	{
		View monsters =
				view.findViewById(R.id.theme_monsters_container);
		final Theme themeMonsters =
				Themes.createMonsterTheme();

		setStars(
				(ImageView) monsters.findViewById(R.id.theme_monsters),
				themeMonsters,
				"monsters");

		return invokeOnClickListener( monsters, themeMonsters );
	}

	/**
	 *
	 * @param view
	 * @param theme
	 * @return @{@link View}
	 */
	private View invokeOnClickListener(View view, final Theme theme)
	{
		view.setOnClickListener(
				new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Shared.eventBus.notify(new ThemeSelectedEvent(theme));
					}
				});
		return  view;
	}

}
