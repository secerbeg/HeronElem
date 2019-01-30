package com.secerbeg.matches.events;

/**
 *
 *
 * @author Mirza Secerbegovic
 */
public abstract class AbstractEvent implements Event
{
	protected abstract void fire(EventObserver eventObserver);
}
