package com.secerbeg.matches.engine;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.ImageView;

import com.secerbeg.matches.R;
import com.secerbeg.matches.common.Memory;
import com.secerbeg.matches.common.Music;
import com.secerbeg.matches.common.Shared;
import com.secerbeg.matches.day.Day;
import com.secerbeg.matches.day.Days;
import com.secerbeg.matches.engine.ScreenController.Screen;
import com.secerbeg.matches.events.EventObserverAdapter;
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
import com.secerbeg.matches.model.BoardArrangment;
import com.secerbeg.matches.model.BoardConfiguration;
import com.secerbeg.matches.model.Game;
import com.secerbeg.matches.model.GameState;

import com.secerbeg.matches.ui.PopupManager;
import com.secerbeg.matches.utils.Clock;
import com.secerbeg.matches.utils.Utils;
import com.secerbeg.matches.week.Week;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Mirza Secerbegovic
 */
public class Engine extends EventObserverAdapter
{

	private static Engine mInstance = null;
	private Game mPlayingGame = null;
	private int mFlippedId = -1;
	private int mToFlip = -1;
	private ScreenController mScreenController;
	private Day mSelectedDay;
	private Week mSelectedWeek;
	private ImageView mBackgroundImage;
	private Handler mHandler;

	private Engine()
	{
		mScreenController = ScreenController.getInstance();
		mHandler = new Handler();
	}

	public static Engine getInstance()
	{
		if (mInstance == null)
		{
			mInstance = new Engine();
		}
		return mInstance;
	}

	public void start()
	{
		Shared.eventBus.listen(DifficultySelectedEvent.TYPE, this);
		Shared.eventBus.listen(FlipCardEvent.TYPE, this);
		Shared.eventBus.listen(StartEvent.TYPE, this);
		Shared.eventBus.listen(BackGameEvent.TYPE, this);
		Shared.eventBus.listen(NextGameEvent.TYPE, this);
		Shared.eventBus.listen(ResetBackgroundEvent.TYPE, this);
		Shared.eventBus.listen(WeekdaySelectedEvent.TYPE, this);
        Shared.eventBus.listen(WeekSelectedEvent.TYPE, this);
	}

	public void stop()
	{
		mPlayingGame = null;
		mBackgroundImage.setImageDrawable(null);
		mBackgroundImage = null;
		mHandler.removeCallbacksAndMessages(null);
		mHandler = null;

		Shared.eventBus.unlisten(DifficultySelectedEvent.TYPE, this);
		Shared.eventBus.unlisten(FlipCardEvent.TYPE, this);
		Shared.eventBus.unlisten(StartEvent.TYPE, this);
		Shared.eventBus.unlisten(BackGameEvent.TYPE, this);
		Shared.eventBus.unlisten(NextGameEvent.TYPE, this);
		Shared.eventBus.unlisten(ResetBackgroundEvent.TYPE, this);
		Shared.eventBus.unlisten(WeekdaySelectedEvent.TYPE, this);
        Shared.eventBus.unlisten(WeekSelectedEvent.TYPE, this);
		mInstance = null;
	}

	@Override
	public void onEvent(ResetBackgroundEvent event)
	{
		Drawable drawable = mBackgroundImage.getDrawable();
		if (drawable != null)
		{
			((TransitionDrawable) drawable).reverseTransition(2000);
		}
		else
		{
			new AsyncTask<Void, Void, Bitmap>()
			{
				@Override
				protected Bitmap doInBackground(Void... params)
				{
					Bitmap bitmap = Utils.scaleDown(R.drawable.background, Utils.screenWidth(), Utils.screenHeight());
					return bitmap;
				}
				protected void onPostExecute(Bitmap bitmap)
				{
					mBackgroundImage.setImageBitmap(bitmap);
				};
			}.execute();
		}
	}

	@Override
	public void onEvent(StartEvent event)
	{
//		mScreenController.openScreen(Screen.WEEKDAY_SELECT);
		mScreenController.openScreen(Screen.WEEK_SELECT);
	}

	@Override
	public void onEvent(NextGameEvent event)
	{
		PopupManager.closePopup();

		int difficulty = mPlayingGame.boardConfiguration.difficulty;

		if (mPlayingGame.gameState.achievedStars == 3 && difficulty < 6)
		{
			difficulty++;
		}
		Shared.eventBus.notify(new DifficultySelectedEvent(difficulty));
	}

	@Override
	public void onEvent(BackGameEvent event)
	{
	    PopupManager.closePopup();
		mScreenController.openScreen(Screen.DIFFICULTY);
	}

	@Override
	public void onEvent(WeekSelectedEvent event)
	{
        mSelectedWeek = event.week;
        mScreenController.openScreen(Screen.WEEKDAY_SELECT);


	}

	@Override
	public void onEvent(WeekdaySelectedEvent event)
	{
		mSelectedDay = event.day;
		mScreenController.openScreen(Screen.DIFFICULTY);

		AsyncTask<Void, Void, TransitionDrawable> task = new AsyncTask<Void, Void, TransitionDrawable>()
		{
			@Override
			protected TransitionDrawable doInBackground(Void... params)
			{
				Bitmap bitmap =
                        Utils.scaleDown(R.drawable.background, Utils.screenWidth(), Utils.screenHeight());
				Bitmap backgroundImage =
                        Days.getBackgroundImage(mSelectedDay);
				backgroundImage =
                        Utils.crop(backgroundImage, Utils.screenHeight(), Utils.screenWidth());
				Drawable backgrounds[] = new Drawable[2];
				backgrounds[0] = new BitmapDrawable(Shared.context.getResources(), bitmap);
				backgrounds[1] = new BitmapDrawable(Shared.context.getResources(), backgroundImage);
				TransitionDrawable crossfader = new TransitionDrawable(backgrounds);
				return crossfader;
			}

			@Override
			protected void onPostExecute(TransitionDrawable result)
			{
				super.onPostExecute(result);
				mBackgroundImage.setImageDrawable(result);
				result.startTransition(2000);
			}
		};
		task.execute();
	}

	/**
	 *
	 * @param event
	 */

	@Override
	public void onEvent(DifficultySelectedEvent event)
	{
		mFlippedId = -1;
		mPlayingGame = new Game();
		mPlayingGame.boardConfiguration = new BoardConfiguration(event.difficulty);
		mPlayingGame.day = mSelectedDay;
		mToFlip = mPlayingGame.boardConfiguration.numTiles;

		// arrange board
		arrangeBoard();

		// start the screen
		mScreenController.openScreen(Screen.GAME);
	}

	private void arrangeBoard()
	{
		BoardConfiguration boardConfiguration = mPlayingGame.boardConfiguration;
		BoardArrangment boardArrangment = new BoardArrangment();

		// build pairs
		// result {0,1,2,...n} // n-number of tiles
		List<Integer> ids = new ArrayList<Integer>();

		int tilesAllowed =
				getMaxPossibleTiles(boardConfiguration);

		for (int i = 0; i < tilesAllowed; i++)
		{
			ids.add(i);
		}
		// shuffle
		// result {4,10,2,39,...}
		Collections.shuffle(ids);

		// place the board
		List<String> tileImageUrls = mPlayingGame.day.tileImageUrls;
		Collections.shuffle(tileImageUrls);
		boardArrangment.pairs = new HashMap<Integer, Integer>();
		boardArrangment.tileUrls = new HashMap<Integer, String>();
		int j = 0;
		for (int i = 0; i < ids.size(); i++)
		{
			if (i + 1 < ids.size())
			{
				// {4,10}, {2,39}, ...
				boardArrangment.pairs.put(ids.get(i), ids.get(i + 1));
				// {10,4}, {39,2}, ...
				boardArrangment.pairs.put(ids.get(i + 1), ids.get(i));
				// {4,
				boardArrangment.tileUrls.put(ids.get(i), tileImageUrls.get(j));
				boardArrangment.tileUrls.put(ids.get(i + 1), tileImageUrls.get(j));
				i++;
				j++;
			}
		}

		mPlayingGame.boardArrangment = boardArrangment;
	}

	/**
	 * Captures max possible tiles
	 * @param boardConfiguration
	 * @return max possible tiles
	 */
	private int getMaxPossibleTiles(BoardConfiguration boardConfiguration)
	{
		int maxPossibleTiles =
				mPlayingGame.day.tileImageUrls.size()* 2;
		if (boardConfiguration.numTiles < maxPossibleTiles)
		{
			maxPossibleTiles = boardConfiguration.numTiles;
		}
		return maxPossibleTiles;
	}

	@Override
	public void onEvent(FlipCardEvent event)
	{
		// Log.i("my_tag", "Flip: " + event.id);
		int id = event.id;
		if (mFlippedId == -1)
		{
			mFlippedId = id;
			// Log.i("my_tag", "Flip: mFlippedId: " + event.id);
		} else
			{

				if (mPlayingGame.boardArrangment.isPair(mFlippedId, id))
				{
				// Log.i("my_tag", "Flip: is pair: " + mFlippedId + ", " + id);
				// send event - hide id1, id2
				Shared.eventBus.notify(new HidePairCardsEvent(mFlippedId, id), 1000);
				// play music
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						Music.playCorrent();
					}
				}, 1000);
				mToFlip -= 2;
				if (mToFlip == 0)
				{
					int passedSeconds = (int) (Clock.getInstance().getPassedTime() / 1000);
					Clock.getInstance().pause();
					int totalTime = mPlayingGame.boardConfiguration.time;
					GameState gameState = new GameState();
					mPlayingGame.gameState = gameState;
					// remained seconds
					gameState.remainedSeconds = totalTime - passedSeconds;
					gameState.passedSeconds = passedSeconds;

					// calc stars
					if (passedSeconds <= totalTime / 2)
					{
						gameState.achievedStars = 3;
					} else if (passedSeconds <= totalTime - totalTime / 5)
					{
						gameState.achievedStars = 2;
					} else if (passedSeconds < totalTime)
					{
						gameState.achievedStars = 1;
					} else


						{
						 gameState.achievedStars = 0;
						}

					// calc score
					gameState.achievedScore =
							mPlayingGame.boardConfiguration.difficulty *
									gameState.remainedSeconds *
									mPlayingGame.day.id;

					// save to memory
					Memory.save(
							mPlayingGame.day.id,
							mPlayingGame.boardConfiguration.difficulty,
							gameState.achievedStars);
					Memory.saveTime(mPlayingGame.day.id,
							mPlayingGame.boardConfiguration.difficulty,
							gameState.passedSeconds);
					Shared.eventBus.notify(new GameWonEvent(gameState), 1200);
				}
			} else
				{
				// Log.i("my_tag", "Flip: all down");
				// send event - flip all down
				Shared.eventBus.notify(new FlipDownCardsEvent(), 1000);
			}
			mFlippedId = -1;
			// Log.i("my_tag", "Flip: mFlippedId: " + mFlippedId);
		}
	}

	public Game getActiveGame()
	{
		return mPlayingGame;
	}

	public Day getSelectedDay()
	{
		return mSelectedDay;
	}

	public void setBackgroundImageView(ImageView backgroundImage)
	{
		mBackgroundImage = backgroundImage;
	}

    public Week getSelectedWeek()
    {
        return mSelectedWeek;
    }

}