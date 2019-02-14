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
	public static String weekStringValue = "week";
    public static String containerStringValue = "_container";
    int[] weeknumbers = {26, 27, 28};


    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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

        for (int i = 0; i < weeknumbers.length; i++)
        {
            int weekToProcess = weeknumbers[i];
            View weekView = prepareWeek(view, weekToProcess);
            animateShow(weekView);
        }

		return view;
	}


    /**
     *
     * @param view
     * @param weekToProcess
     * @return
     */
	private View prepareWeek(View view, int weekToProcess)
	{
		View weekView =
				view.findViewById(getContainerId(weekToProcess));

		final Week week = getWeek(weekToProcess);

		weekView.setOnClickListener(
				new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Shared.eventBus.notify(new WeekSelectedEvent(week));
					}
				});
              return weekView;
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

    /***
     *
     * @param weekToProcess
     * @return
     */
    public int  getContainerId(int weekToProcess)
    {
         int id  = 0;
        switch (weekToProcess)
        {
            case 26:
                id =  R.id.week26_container;
                break;
            case 27:
                id =  R.id.week27_container;
                break;
            case 28:
                id =  R.id.week28_container;
                break;
            default:
                break;
        }

        return id;
    }

    /**
     *
     * @param week
     */
	public void setWeek(Week week)
	{
		this.week = week;
	}

    /**
     *
     * @return
     */
	public Week  getWeek()
	{
		return this.week;
	}


}
