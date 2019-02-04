package com.secerbeg.matches.model;

import com.secerbeg.matches.day.Day;

/**
 * This is instance of active playing game
 * 
 * @author sromku
 */
public class Game
{
	/**
	 * The board configuration
	 */
	public BoardConfiguration boardConfiguration;

	/**
	 * The board arrangment
	 */
	public BoardArrangment boardArrangment;

	/**
	 * The selected theme
	 */
	public Day day;

	public GameState gameState;

}
