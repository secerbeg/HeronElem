package com.secerbeg.matches.day;

import android.graphics.Bitmap;

import com.secerbeg.matches.common.Shared;
import com.secerbeg.matches.utils.Utils;
import java.util.ArrayList;

public class Days
{
        public static String URI_DRAWABLE = "drawable://";

        /**
         *  Creates Monday Theme and assembles Images associated with .png files
         *
         * @return @{@link Day}
         */
        public static Day createMondayWords()
        {
            Day day = new Day();
            day.id = 2;
            day.name = "Monday Words" ;
            day.backgroundImageUrl = URI_DRAWABLE + "back_words";
            day.tileImageUrls = new ArrayList<String>();

            for (int i = 1; i <= 24; i++)
            {
                day.tileImageUrls.add(URI_DRAWABLE + String.format("week26mon_%d", i));
            }
            return day;
        }

       /**
        *
        *
        * @return @{@link Day}
        */
        public static Day createWednesdayWords()
        {
            Day day = new Day();
            day.id = 3;
            day.name = "Wednesday Words" ;
            day.backgroundImageUrl = URI_DRAWABLE + "back_words";
            day.tileImageUrls = new ArrayList<String>();

            for (int i = 1; i <= 27; i++)
            {
                day.tileImageUrls.add(URI_DRAWABLE + String.format("week26wed_%d", i));
            }
            return day;
        }

      /**
       *
       * @param day
       * @return
       */

        public static Bitmap getBackgroundImage(Day day)
        {
            String drawableResourceName =
                    day.backgroundImageUrl.substring(com.secerbeg.matches.day.Days.URI_DRAWABLE.length());
            int drawableResourceId =
                    Shared.context.getResources().getIdentifier(
                            drawableResourceName,
                            "drawable",
                            Shared.context.getPackageName());
            Bitmap bitmap =
                    Utils.scaleDown(
                            drawableResourceId,
                            Utils.screenWidth(),
                            Utils.screenHeight());
            return bitmap;
        }
}
