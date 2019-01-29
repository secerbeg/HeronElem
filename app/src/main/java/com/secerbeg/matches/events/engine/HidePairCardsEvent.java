package com.secerbeg.matches.events.engine;

import com.secerbeg.matches.events.AbstractEvent;
import com.secerbeg.matches.events.EventObserver;

/**
 * When the 'back to menu' was pressed.
 *
 * @author Mirza Secerbegovic
 */
public class HidePairCardsEvent extends AbstractEvent
{

	public static final String TYPE = HidePairCardsEvent.class.getName();
	public int id1;
	public int id2;

	/**
	 *
	 * @param id1
	 * @param id2
	 */
	public HidePairCardsEvent(int id1, int id2)
	{
		this.id1 = id1;
		this.id2 = id2;
	}

	/**
	 *
	 * @param eventObserver
	 */
	@Override
	protected void fire(EventObserver eventObserver)
	{
		eventObserver.onEvent(this);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String getType()
	{
		return TYPE;
	}

}
