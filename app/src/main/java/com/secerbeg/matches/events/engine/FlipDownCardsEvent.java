package com.secerbeg.matches.events.engine;

import com.secerbeg.matches.events.AbstractEvent;
import com.secerbeg.matches.events.EventObserver;

/**
 * When the 'back to menu' was pressed.
 *
 * @author Mirza Secerbegovic
 */
public class FlipDownCardsEvent extends AbstractEvent
{
	public static final String TYPE = FlipDownCardsEvent.class.getName();

	public FlipDownCardsEvent()
	{
	}
	
	@Override
	protected void fire(EventObserver eventObserver) {
		eventObserver.onEvent(this);
	}

	@Override
	public String getType() {
		return TYPE;
	}

}
