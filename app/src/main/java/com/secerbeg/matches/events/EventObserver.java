package com.secerbeg.matches.events;

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

/**
 *
 * @author Mirza Secerbegovic
 */
public interface EventObserver
{
	void onEvent(FlipCardEvent event);

	void onEvent(DifficultySelectedEvent event);

	void onEvent(HidePairCardsEvent event);

	void onEvent(FlipDownCardsEvent event);

	void onEvent(StartEvent event);

	void onEvent(GameWonEvent event);

	void onEvent(BackGameEvent event);

	void onEvent(NextGameEvent event);

	void onEvent(WeekdaySelectedEvent event);

	void onEvent(ResetBackgroundEvent event);

	void onEvent(WeekSelectedEvent event);


}
