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
    private static String highStartKey = "theme_%d_difficulty_%d";
    private static String bestTimeKey = "themetime_%d_difficultytime_%d";

    /**
     *
     * @param theme
     * @param difficulty
     * @param stars
     */
    public static void save(int theme, int difficulty, int stars)
    {
        int highStars =
                getHighStars(theme, difficulty);

        if (stars > highStars)
        {
            SharedPreferences sharedPreferences =
                    Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit =
                    sharedPreferences.edit();
            String key =
                    String.format(highStartKey, theme, difficulty);
            edit.putInt(key, stars).commit();
        }
    }

    /**
     *
     *
     * @param theme
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
     * @param theme
     * @param difficulty
     * @return
     */
    public static int getHighStars(int theme, int difficulty) {

        SharedPreferences sharedPreferences =
                Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        String key =
                String.format(highStartKey, theme, difficulty);
        return sharedPreferences.getInt(key, 0);
    }

    /**
     *
     * @param theme
     * @param difficulty
     * @return
     */
    public static int getBestTime(int theme, int difficulty)
    {
        SharedPreferences sharedPreferences =
                Shared.context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String key =
                String.format(bestTimeKey, theme, difficulty);
        return sharedPreferences.getInt(key, -1);
    }

}
