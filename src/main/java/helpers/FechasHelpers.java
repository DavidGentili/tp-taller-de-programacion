package helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FechasHelpers {

    public static boolean isOver18(GregorianCalendar birthday){
        return diferenceYears(birthday) >= 18;
    }

    public static int diferenceYears(GregorianCalendar date){
        boolean response = false;
        int DAY = GregorianCalendar.DAY_OF_MONTH;
        int MONTH = GregorianCalendar.MONTH;
        int YEAR = GregorianCalendar.YEAR;
        GregorianCalendar now = (GregorianCalendar) GregorianCalendar.getInstance();
        int yearDif = now.get(YEAR) - date.get(YEAR);
        if(date.get(MONTH) > now.get(MONTH) || (date.get(MONTH) == now.get(MONTH) && date.get(DAY) > now.get(DAY)))
            yearDif--;
        return yearDif;
    }

    public static boolean isSameDay(GregorianCalendar current, GregorianCalendar other){
        int DAY = GregorianCalendar.DAY_OF_MONTH;
        int MONTH = GregorianCalendar.MONTH;
        int YEAR = GregorianCalendar.YEAR;
        return current.get(YEAR) == other.get(YEAR) && current.get(MONTH) == other.get(MONTH) && current.get(DAY) == other.get(DAY);
    }

}
