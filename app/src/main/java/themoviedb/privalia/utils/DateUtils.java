package themoviedb.privalia.utils;

/**
 * Created by Arturo on 08/04/2018.
 */

public class DateUtils {

    public static String getYearFromDate(String fullDate) {
        if(fullDate.contains("-")){
            return fullDate.substring(0, fullDate.indexOf("-"));
        } else {
            return fullDate;
        }
    }
}
