package com.secerbeg.matches.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.secerbeg.matches.R;
import com.secerbeg.matches.common.Shared;
import com.secerbeg.matches.events.engine.FlipDownCardsEvent;
import com.secerbeg.matches.events.engine.GameWonEvent;
import com.secerbeg.matches.events.engine.HidePairCardsEvent;
import com.secerbeg.matches.events.engine.TimeExpiredEvent;
import com.secerbeg.matches.model.Game;
import com.secerbeg.matches.ui.BoardView;
import com.secerbeg.matches.ui.PopupManager;
import com.secerbeg.matches.utils.Clock;
import com.secerbeg.matches.utils.Clock.OnTimerCount;
import com.secerbeg.matches.utils.FontLoader;
import com.secerbeg.matches.utils.FontLoader.Font;
import com.secerbeg.matches.utils.Utils;

public class GameFragment extends BaseFragment
{

	private BoardView mBoardView;
	private TextView mTime;
	private ImageView mTimeImage;
	private LinearLayout ads;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.game_fragment, container, false);
		view.setClipChildren(false);
		((ViewGroup)view.findViewById(R.id.game_board)).setClipChildren(false);
		mTime = (TextView) view.findViewById(R.id.time_bar_text);
		mTimeImage = (ImageView) view.findViewById(R.id.time_bar_image);
		FontLoader.setTypeface(Shared.context, new TextView[] {mTime}, Font.GROBOLD);
		mBoardView = BoardView.fromXml(getActivity().getApplicationContext(), view);
		FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.game_container);
		frameLayout.addView(mBoardView);
		frameLayout.setClipChildren(false);

		// build board
		buildBoard();
		Shared.eventBus.listen(FlipDownCardsEvent.TYPE, this);
		Shared.eventBus.listen(HidePairCardsEvent.TYPE, this);
		Shared.eventBus.listen(GameWonEvent.TYPE, this);
		Shared.eventBus.listen(TimeExpiredEvent.TYPE, this);

		return view;
	}
	
	@Override
	public void onDestroy()
	{
		Shared.eventBus.unlisten(FlipDownCardsEvent.TYPE, this);
		Shared.eventBus.unlisten(HidePairCardsEvent.TYPE, this);
		Shared.eventBus.unlisten(GameWonEvent.TYPE, this);
		Shared.eventBus.unlisten(TimeExpiredEvent.TYPE, this);
		super.onDestroy();
	}

	private void buildBoard()
	{
		Game game = Shared.engine.getActiveGame();
		int time = game.boardConfiguration.time;
		setTime(time);
		mBoardView.setBoard(game);
		
		startClock(time);
		trackTimeInBackground();
	}

	@SuppressLint("StaticFieldLeak")
	private void trackTimeInBackground()
	{

	}


	private void setTime(int time)
	{
		int min = time / 60;
		int sec = time - min*60;
		mTime.setText(" " + String.format("%02d", min) + ":" + String.format("%02d", sec));
	}

	private void startClock(int sec)
	{
		Clock clock = Clock.getInstance();

		clock.startTimer(sec*1000, 1000, new OnTimerCount()
		{
			
			@Override
			public void onTick(long millisUntilFinished)
			{
				setTime((int) (millisUntilFinished/1000));
			}
			
			@Override
			public void onFinish()
			{
				setTime(0);
			}
		});
	}

	@Override
	public void onEvent(GameWonEvent event)
	{
		mTime.setVisibility(View.GONE);
		mTimeImage.setVisibility(View.GONE);
		PopupManager.showPopupWon(event.gameState);
	}

	@Override
	public void onEvent(TimeExpiredEvent event)
	{
		mTime.setVisibility(View.GONE);
		mTimeImage.setVisibility(View.GONE);
		PopupManager.showPopupWon(event.gameState);
	}

	@Override
	public void onEvent(FlipDownCardsEvent event)
	{
		mBoardView.flipDownAll();
	}

	@Override
	public void onEvent(HidePairCardsEvent event)
	{
		mBoardView.hideCards(event.id1, event.id2);
	}

}
