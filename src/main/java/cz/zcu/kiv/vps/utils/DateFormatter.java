package cz.zcu.kiv.vps.utils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Lukas Cerny.
 */
public class DateFormatter {

    /**
     * Static method formats date to full format.
     * @param date
     * @return
     */
    public static String formatToFull(Date date) {
        return format(date, DateFormat.FULL);
    }

    /**
     * Static method formats date to long format.
     * @param date
     * @return
     */
    public static String formatToLong(Date date) {
        return format(date, DateFormat.LONG);
    }

    /**
     * Static method formats date to medium format.
     * @param date
     * @return
     */
    public static String formatToMedium(Date date) {
        return format(date, DateFormat.MEDIUM);
    }

    /**
     * Static method formats date to short format.
     * @param date
     * @return
     */
    public static String formatToShort(Date date) {
        return format(date, DateFormat.SHORT);
    }

    /**
     * Static method formats date to string format.
     * @param date
     * @param formatType
     * @return
     */
    private static String format(Date date, int formatType) {
        return DateFormat.getDateInstance(formatType, Locale.getDefault()).format(date);
    }
}
