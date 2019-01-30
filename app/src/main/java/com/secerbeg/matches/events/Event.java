package com.secerbeg.matches.events;


/**
 * The event that is invoked from the low levels of this game (like engine) and
 * not from the ui.
 * 
 * @author Mirza Secerbegovic
 */
public interface Event
{
	String getType();
}
