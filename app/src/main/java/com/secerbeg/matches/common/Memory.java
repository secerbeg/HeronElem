package com.secerbeg.matches.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * @author Mirza Secerbegovic
 */
public class Memory
{

    private static final String SHARED_PREFERENCES_NAME = "com.secerbeg.matches";
    private static String highStartKey = "day%d_difficulty_%d";
    private static String bestTimeKey = "daytime_%d_difficultytime_%d";

    /**
     *
     * @param day
     * @param difficulty
     * @param stars
     */
    public static void save(int day, int difficulty, int stars)
    {
        int highStars =
                getHighStars(day, difficulty);

        if (stars > highStars)
        {
            SharedPreferences sharedPreferences =
                    Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit =  sharedPreferences.edit();
            String key = String.format(highStartKey, day, difficulty);
            edit.putInt(key, stars).commit();
        }
    }

    /**
     *
     *
     * @param day
     * @param difficulty
     * @param passedSecs
     */
    public static void saveTime(int theme, int difficulty, int passedSecs)
    {
        int bestTime = getBestTime(theme, difficulty);

        if (passedSecs < bestTime || bestTime == -1)
        {
            SharedPreferences sharedPreferences =
                    Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor =
                    sharedPreferences.edit();
            String timeKey =
                    String.format(bestTimeKey, theme, difficulty);
            editor.putInt(timeKey, passedSecs);
            editor.commit();
        }
    }

    /**
     *
     * @param day
     * @param difficulty
     * @return
     */
    public static int getHighStars(int day, int difficulty) {

        SharedPreferences sharedPreferences =
                Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        String key =
                String.format(highStartKey, day, difficulty);
        return sharedPreferences.getInt(key, 0);
    }

    /**
     *
     * @param day
     * @param difficulty
     * @return
     */
    public static int getBestTime(int day, int difficulty)
    {
        SharedPreferences sharedPreferences =
                Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String key =
                String.format(bestTimeKey, day, difficulty);
        return sharedPreferences.getInt(key, -1);
    }

}
