package cz.zcu.kiv.vps.utils;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Created by Lukas Cerny.
 */
public class DateFormatterTest {

    private Date date;

    @Before
    public void setUp() throws ParseException {
        date = new GregorianCalendar(1970, Calendar.DECEMBER, 31).getTime();
    }

    @Test
    public void formatToFullCZ() throws Exception {
        Locale.setDefault(new Locale("cs", "CZ"));
        assertEquals("čtvrtek 31. prosince 1970", DateFormatter.formatToFull(date));
    }

    @Test
    public void formatToFullEN() throws Exception {
        Locale.setDefault(Locale.US);
        assertEquals("Thursday, December 31, 1970", DateFormatter.formatToFull(date));
    }

    @Test
    public void formatToLongCZ() throws Exception {
        Locale.setDefault(new Locale("cs", "CZ"));
        assertEquals("31. prosince 1970", DateFormatter.formatToLong(date));
    }

    @Test
    public void formatToLongEN() throws Exception {
        Locale.setDefault(Locale.US);
        assertEquals("December 31, 1970", DateFormatter.formatToLong(date));
    }

    @Test
    public void formatToMediumCZ() throws Exception {
        Locale.setDefault(new Locale("cs", "CZ"));
        assertEquals("31. 12. 1970", DateFormatter.formatToMedium(date));
    }

    @Test
    public void formatToMediumEN() throws Exception {
        Locale.setDefault(Locale.US);
        assertEquals("Dec 31, 1970", DateFormatter.formatToMedium(date));
    }

    @Test
    public void formatToShortCZ() throws Exception {
        Locale.setDefault(new Locale("cs", "CZ"));
        assertEquals("31.12.70", DateFormatter.formatToShort(date));
    }

    @Test
    public void formatToShortEN() throws Exception {
        Locale.setDefault(Locale.US);
        assertEquals("12/31/70", DateFormatter.formatToShort(date));
    }
}