package com.secerbeg.matches.fragments;

import android.support.v4.app.Fragment;

import com.secerbeg.matches.events.EventObserver;
import com.secerbeg.matches.events.engine.FlipDownCardsEvent;
import com.secerbeg.matches.events.engine.GameWonEvent;
import com.secerbeg.matches.events.engine.HidePairCardsEvent;
import com.secerbeg.matches.events.ui.BackGameEvent;
import com.secerbeg.matches.events.ui.DifficultySelectedEvent;
import com.secerbeg.matches.events.ui.FlipCardEvent;
import com.secerbeg.matches.events.ui.NextGameEvent;
import com.secerbeg.matches.events.ui.ResetBackgroundEvent;
import com.secerbeg.matches.events.ui.StartEvent;
import com.secerbeg.matches.events.ui.WeekSelectedEvent;
import com.secerbeg.matches.events.ui.WeekdaySelectedEvent;

public class BaseFragment extends Fragment implements EventObserver
{

	@Override
	public void onEvent(FlipCardEvent event)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(DifficultySelectedEvent event)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(HidePairCardsEvent event)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(FlipDownCardsEvent event)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(StartEvent event)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(GameWonEvent event)

	{
		throw new UnsupportedOperationException();
	}
	@Override
	public void onEvent(BackGameEvent event)

	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(NextGameEvent event)

	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(ResetBackgroundEvent event)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(WeekdaySelectedEvent event)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void onEvent(WeekSelectedEvent event)
	{
		throw new UnsupportedOperationException();
	}


}
