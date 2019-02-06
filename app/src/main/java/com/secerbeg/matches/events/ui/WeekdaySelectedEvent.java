package com.secerbeg.matches.events.ui;

import com.secerbeg.matches.day.Day;
import com.secerbeg.matches.events.AbstractEvent;
import com.secerbeg.matches.events.EventObserver;

public class WeekdaySelectedEvent extends AbstractEvent
{

	public static final String TYPE = WeekdaySelectedEvent.class.getName();
	public final Day day;

	public WeekdaySelectedEvent(Day day)
	{
		this.day = day;
	}

	@Override
	protected void fire(EventObserver eventObserver)
	{
		eventObserver.onEvent(this);
	}

	@Override
	public String getType()
	{
		return TYPE;
	}

}
