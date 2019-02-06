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
import com.secerbeg.matches.day.Day;
import com.secerbeg.matches.day.Days;
import com.secerbeg.matches.events.ui.WeekdaySelectedEvent;
import static com.secerbeg.matches.R.id.day_wednesday;

public class WeekdaySelectFragment extends Fragment
{
	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState)
	{
		View view =
				LayoutInflater.from(Shared.context).inflate(
						R.layout.weekday_select_fragment,
						container,
						false);



		View mondayWords =
				this.prepareMondayWords(view);
		View wednesdayWords =
				this.prepareWednesdayWords(view);

		animateShow(mondayWords);
        animateShow(wednesdayWords);

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
	 * @param day
	 * @param type
	 */
	private void setStars(ImageView imageView, Day day, String type)
	{
		int sum = 0;
		for (int difficulty = 1; difficulty <= 6; difficulty++)
		{
			sum += Memory.getHighStars(day.id, difficulty);
		}
		int num = sum / 6;

//		if (num != 0)
//		{
//			String drawableResourceName = String.format(Locale.US, type + "_day_star_%d", num);
//
//			int drawableResourceId =
//					Shared.context.getResources().getIdentifier(
//							drawableResourceName,
//							"drawable",
//							Shared.context.getPackageName());
//
//			imageView.setImageResource(drawableResourceId);
//		}
	}

	/**
	 *
	 * @param view
	 * @return @{@link View}
	 */
	private View  prepareMondayWords (View view)
	{
		View words =
				view.findViewById(R.id.day_monday_container);

		final Day mondayWords =
				Days.createMondayWords();

		setStars(
				(ImageView) words.findViewById(R.id.day_monday),
				mondayWords,
				"words");

		words.setOnClickListener(
				new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Shared.eventBus.notify(new WeekdaySelectedEvent(mondayWords));
					}
				});


		return words;
	}


	/**
	 *
	 * @param view
	 * @return
	 */
	private View  prepareWednesdayWords(View view)
	{
		View words =
				view.findViewById(R.id.day_wednesday_container);

		final Day wednesdayWords = Days.createWednesdayWords();

		setStars((ImageView) words.findViewById(
				day_wednesday),
				wednesdayWords,
				"words");

		words.setOnClickListener(
				new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Shared.eventBus.notify(new WeekdaySelectedEvent(wednesdayWords));
					}
				});

		return words;

	}
}
