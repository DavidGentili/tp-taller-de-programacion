package helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FechasHelpers {

    public static boolean isOver18(GregorianCalendar birthday){
        boolean response = false;
        int DAY = GregorianCalendar.DAY_OF_MONTH;
        int MONTH = GregorianCalendar.MONTH;
        int YEAR = GregorianCalendar.YEAR;
        GregorianCalendar now = (GregorianCalendar) GregorianCalendar.getInstance();
        int yearDif = now.get(YEAR) - birthday.get(YEAR);
        if(birthday.get(MONTH) > now.get(MONTH) || (birthday.get(MONTH) == now.get(MONTH) && birthday.get(DAY) > now.get(DAY)))
            yearDif--;
        return yearDif >= 18;
    }

    public static boolean isSameDay(GregorianCalendar current, GregorianCalendar other){
        int DAY = GregorianCalendar.DAY_OF_MONTH;
        int MONTH = GregorianCalendar.MONTH;
        int YEAR = GregorianCalendar.YEAR;
        return current.get(YEAR) == other.get(YEAR) && current.get(MONTH) == other.get(MONTH) && current.get(DAY) == other.get(DAY);
    }

}
