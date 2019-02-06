package com.secerbeg.matches.events.ui;

import com.secerbeg.matches.day.Day;
import com.secerbeg.matches.events.AbstractEvent;
import com.secerbeg.matches.events.EventObserver;
import com.secerbeg.matches.week.Week;

public class WeekSelectedEvent extends AbstractEvent
{

	public static final String TYPE = WeekSelectedEvent.class.getName();
	public final Week week;

	public WeekSelectedEvent(Week week)
	{
		this.week = week;
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
