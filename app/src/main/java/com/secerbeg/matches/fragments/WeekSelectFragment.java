package com.secerbeg.matches.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.secerbeg.matches.R;
import com.secerbeg.matches.common.Shared;
import com.secerbeg.matches.events.ui.WeekSelectedEvent;
import com.secerbeg.matches.week.Week;

public class WeekSelectFragment extends Fragment
{
	private Week week;

	public static String URI_DRAWABLE = "drawable://";

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState)
	{
		View view =
				LayoutInflater.from(
						Shared.context).inflate
						(R.layout.week_select_fragment,
						 container,
						false);

		prepareWeek26(view);
		prepareWeek27(view);


		return view;
	}

	private void prepareWeek26(View view)
	{
		View week26 =
				view.findViewById(R.id.week26_container);

		final Week week = getWeek(26);

		week26.setOnClickListener(
			 new View.OnClickListener()
			 {
				 @Override
				 public void onClick(View v)
				 {
					 Shared.eventBus.notify(new WeekSelectedEvent(week));
				 }
			 });

		animateShow(week26);
		//animateShow(wednesdayWords);
	}


	private void prepareWeek27(View view)
	{
		View week27 =
				view.findViewById(R.id.week27_container);

		final Week week = getWeek(27);

		week27.setOnClickListener(
				new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Shared.eventBus.notify(new WeekSelectedEvent(week));
					}
				});
	}

	/**
	 *
	 * @param weekNumber
	 * @return
	 */
	private Week getWeek(int weekNumber)
	{
		Week week = 	new Week();
		week.id = weekNumber;
		week.name = String.valueOf(weekNumber);
		week.backgroundImageUrl = URI_DRAWABLE + "back_week";
		return week;
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

	public void setWeek(Week week)
	{
		this.week = week;
	}

	public Week  getWeek()
	{
		return this.week;
	}


}
