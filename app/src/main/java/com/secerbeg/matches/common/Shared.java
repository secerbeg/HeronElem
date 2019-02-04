package com.secerbeg.matches.common;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.secerbeg.matches.engine.Engine;
import com.secerbeg.matches.events.EventBus;


/**
 *
 * @author Mirza Secerbegovic
 */
public class Shared
{
	public static Context context;
	public static FragmentActivity activity; // it's fine for this app, but better move to weak reference
	public static Engine engine;
	public static EventBus eventBus;

}
