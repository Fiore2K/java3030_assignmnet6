package edu.uml.kfiore.model;


import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for Quotes class
 */
public class QuotesTest {

    public static final Calendar timeCalendar = Calendar.getInstance();

    static {
        timeCalendar.set(2004, Calendar.AUGUST, 19);
    }

    final static String stockSymbol = "GOOG";
    final static int price = 100;
    public static final Timestamp time = new Timestamp(timeCalendar.getTimeInMillis());

    /**
     * Testing helper method for generating Quotes test data
     *
     * @return a Quotes object that uses static constants for data.
     */
    public static Quotes createQuotes() {
        Quotes quotes = new Quotes();
        quotes.setStockSymbol(stockSymbol);
        quotes.setTime(time);
        quotes.setPrice(price);
        return quotes;
    }

    @Test
    public void testQuotesSettersAndGetters() {
        Quotes quotes = createQuotes();
        int id = 10;
        quotes.setId(id);
        assertEquals("time", time, quotes.getTime());
        assertEquals("stockSymbol", stockSymbol, quotes.getStockSymbol());
        assertEquals("price", price, this.price, quotes.getPrice());
        assertEquals("id", id, quotes.getId());

    }

}

