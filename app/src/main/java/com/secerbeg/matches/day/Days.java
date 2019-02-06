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
            day.id = 1;
            day.name = "Monday Words" ;
            day.backgroundImageUrl = URI_DRAWABLE + "background_png";
            day.tileImageUrls = new ArrayList<String>();
            String weekFormat = getWeekFormat();
            for (int i = 1; i <= 24; i++)
            {
                day.tileImageUrls.add(URI_DRAWABLE + String.format(weekFormat, i));
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
            day.id = 2;
            day.name = "Wednesday Words" ;
            day.backgroundImageUrl = URI_DRAWABLE + "background_png";
            day.tileImageUrls = new ArrayList<String>();

            String weekFormat = getWeekFormat();
            for (int i = 1; i <= 27; i++)
            {
                day.tileImageUrls.add(URI_DRAWABLE + String.format( weekFormat, i));
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


        public static String getWeekFormat()
        {
         int weekNumber = Shared.engine.getSelectedWeek().id;

         String weekFormat = null;

            switch (weekNumber)
            {
                case 26:
                    weekFormat =  "week26mon_%d";
                    break;
                case 27:
                    weekFormat =  "week27mon_%d";
                    break;
                case 28:
                    weekFormat =  "week28mon_%d";
                    break;
                case 29:
                    weekFormat =  "week29mon_%d";
                    break;
                    default:
                        break;
            }

            return weekFormat;
        }
}
